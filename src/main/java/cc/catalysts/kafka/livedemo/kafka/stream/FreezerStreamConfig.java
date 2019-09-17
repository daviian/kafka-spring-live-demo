package cc.catalysts.kafka.livedemo.kafka.stream;

import cc.catalysts.kafka.livedemo.model.Freezer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.CleanupConfig;

import java.util.Map;

@Configuration
public class FreezerStreamConfig extends AbstractStreamConfig {

    @Bean(name = "FreezerStreamsConfig")
    public KafkaStreamsConfiguration freezerStreamsConfigs(KafkaProperties properties) {
        Map<String, Object> config = getDefaults(properties);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "freezer-stream");
        return new KafkaStreamsConfiguration(config);
    }

    @Bean(name = "FreezerStreamsBuilder")
    public StreamsBuilderFactoryBean freezerStreamBuilder(@Qualifier("FreezerStreamsConfig") KafkaStreamsConfiguration streamsConfig) {
        return new StreamsBuilderFactoryBean(streamsConfig, new CleanupConfig(true, false));
    }

    @Bean
    public GlobalKTable<String, Freezer> describeFreezerStream(FreezerStream freezerStream) {
        return freezerStream.describe();
    }

}
