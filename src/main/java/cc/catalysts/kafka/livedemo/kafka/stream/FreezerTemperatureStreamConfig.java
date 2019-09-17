package cc.catalysts.kafka.livedemo.kafka.stream;

import cc.catalysts.kafka.livedemo.model.FreezerTemperature;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.CleanupConfig;

import java.util.Map;

@Configuration
public class FreezerTemperatureStreamConfig extends AbstractStreamConfig {

    @Bean(name = "FreezerTemperatureStreamsConfig")
    public KafkaStreamsConfiguration freezerTemperatureStreamsConfig(KafkaProperties properties) {
        Map<String, Object> config = getDefaults(properties);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "freezer-temperature-join-stream");
        return new KafkaStreamsConfiguration(config);
    }

    @Bean(name = "FreezerTemperatureStreamsBuilder")
    public StreamsBuilderFactoryBean freezerTemperatureStreamBuilder(@Qualifier("FreezerTemperatureStreamsConfig") KafkaStreamsConfiguration streamsConfig) {
        return new StreamsBuilderFactoryBean(streamsConfig, new CleanupConfig(true, false));
    }

    @Bean
    public KStream<String, FreezerTemperature> describeFreezerTemperatureStream(FreezerTemperatureStream freezerTemperatureStream) {
        return freezerTemperatureStream.describe();
    }

}
