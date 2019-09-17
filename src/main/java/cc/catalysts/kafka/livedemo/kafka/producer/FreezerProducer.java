package cc.catalysts.kafka.livedemo.kafka.producer;

import cc.catalysts.kafka.livedemo.kafka.Topic;
import cc.catalysts.kafka.livedemo.model.Freezer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class FreezerProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper mapper;

    public FreezerProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void publish(UUID id, Freezer freezer) throws IOException {
        String value = mapper.writeValueAsString(freezer);
        this.kafkaTemplate.send(Topic.FREEZER, id.toString(), value);
    }

}
