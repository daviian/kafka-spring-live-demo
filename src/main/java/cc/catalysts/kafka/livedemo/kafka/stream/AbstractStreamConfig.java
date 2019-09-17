package cc.catalysts.kafka.livedemo.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.HashMap;
import java.util.Map;

public class AbstractStreamConfig {

    protected Map<String, Object> getDefaults(KafkaProperties properties) {
        Map<String, Object> config = new HashMap<>(properties.getStreams().buildProperties());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return config;
    }


}
