package cc.catalysts.kafka.livedemo.kafka.stream;

import cc.catalysts.kafka.livedemo.model.Freezer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.CleanupConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FreezerStreamConfig {

    @Bean(name = "FreezerStreamsConfig")
    public KafkaStreamsConfiguration freezerStreamsConfigs(KafkaProperties properties) {
        Map<String, Object> config = new HashMap<>(properties.getStreams().buildProperties());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "freezer-stream");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
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
