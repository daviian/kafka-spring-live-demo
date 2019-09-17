package cc.catalysts.kafka.livedemo.kafka.stream;

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
public class AvgTemperatureStreamConfig extends AbstractStreamConfig {

    @Bean(name = "AvgTemperatureStreamsConfig")
    public KafkaStreamsConfiguration avgTemperatureStreamsConfigs(KafkaProperties properties) {
        Map<String, Object> config = getDefaults(properties);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "avg-temperature-stream");
        return new KafkaStreamsConfiguration(config);
    }

    @Bean(name = "AvgTemperatureStreamsBuilder")
    public StreamsBuilderFactoryBean avgTemperatureStreamBuilder(@Qualifier("AvgTemperatureStreamsConfig") KafkaStreamsConfiguration streamsConfig) {
        return new StreamsBuilderFactoryBean(streamsConfig, new CleanupConfig(true, false));
    }

    @Bean
    public KStream<String, Double> describeAvgTemperatureStream(AvgTemperatureStream avgTemperatureStream) {
        return avgTemperatureStream.describe();
    }

}
