package cc.catalysts.kafka.livedemo.kafka.stream;

import cc.catalysts.kafka.livedemo.kafka.Topic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AvgTemperatureStream {

    private final StreamsBuilder kStreamBuilder;

    private Serde<String> stringSerde = new Serdes.StringSerde();
    private Serde<Double> temperatureSerde = new JsonSerde<>(Double.class);
    private Serde<AvgTemperature> avgTemperatureSerde = new JsonSerde<>(AvgTemperature.class);

    public AvgTemperatureStream(@Qualifier("AvgTemperatureStreamsBuilder") StreamsBuilder kStreamBuilder) {
        this.kStreamBuilder = kStreamBuilder;
    }

    KStream<String, Double> describe() {
        KStream<String, Double> temperatureStream = kStreamBuilder.stream(
                Topic.FREEZER_TEMPERATURE, Consumed.with(stringSerde, temperatureSerde));

        long windowSizeMs = TimeUnit.SECONDS.toMillis(60);
        long advanceBySizeMs = TimeUnit.SECONDS.toMillis(10);

        KStream<String, Double> avgTemperatureStream = temperatureStream
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSizeMs).advanceBy(advanceBySizeMs))
                .aggregate(
                        AvgTemperature::new,
                        (key, temperature, avgTemperature) -> avgTemperature.add(temperature),
                        Materialized.with(stringSerde, avgTemperatureSerde)
                )
                .mapValues(AvgTemperature::average)
                .toStream()
                .map((windowedKey, temperature) -> KeyValue.pair(windowedKey.key(), temperature));

        avgTemperatureStream
                .to(Topic.FREEZER_AVG_TEMPERATURE, Produced.with(stringSerde, temperatureSerde));

        return avgTemperatureStream;
    }

}
