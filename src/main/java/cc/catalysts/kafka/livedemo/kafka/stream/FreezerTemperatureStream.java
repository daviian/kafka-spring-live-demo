package cc.catalysts.kafka.livedemo.kafka.stream;

import cc.catalysts.kafka.livedemo.kafka.Topic;
import cc.catalysts.kafka.livedemo.model.Freezer;
import cc.catalysts.kafka.livedemo.model.FreezerTemperature;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class FreezerTemperatureStream {

    private final StreamsBuilder kStreamBuilder;

    private Serde<String> stringSerde = new Serdes.StringSerde();
    private Serde<Double> temperatureSerde = new JsonSerde<>(Double.class);

    private Serde<Freezer> jsonFreezerSerde = new JsonSerde<>(Freezer.class);
    private Serde<FreezerTemperature> jsonFreezerTemperatureSerde = new JsonSerde<>(FreezerTemperature.class);

    public FreezerTemperatureStream(@Qualifier("FreezerTemperatureStreamsBuilder") StreamsBuilder kStreamBuilder) {
        this.kStreamBuilder = kStreamBuilder;
    }

    KStream<String, FreezerTemperature> describe() {
        KTable<String, Freezer> freezerTable = kStreamBuilder.table(
                Topic.FREEZER, Consumed.with(stringSerde, jsonFreezerSerde));

        KTable<String, Double> temperatureTable = kStreamBuilder.table(
                Topic.FREEZER_TEMPERATURE, Consumed.with(stringSerde, temperatureSerde));

        KStream<String, FreezerTemperature> freezerTemperatureStream = freezerTable
                .leftJoin(temperatureTable, this::join)
                .toStream();

        freezerTemperatureStream
                .to(Topic.FREEZER_TEMPERATURE_JOIN, Produced.with(stringSerde, jsonFreezerTemperatureSerde));

        return freezerTemperatureStream;
    }

    private FreezerTemperature join(Freezer freezer, Double temperature) {
        FreezerTemperature freezerTemperature = new FreezerTemperature();
        freezerTemperature.setId(freezer.getId());
        freezerTemperature.setLocation(freezer.getLocation());
        freezerTemperature.setName(freezer.getName());
        freezerTemperature.setTemperature(temperature);

        return freezerTemperature;
    }

}
