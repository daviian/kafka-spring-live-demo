package cc.catalysts.kafka.livedemo.kafka.producer;

import cc.catalysts.kafka.livedemo.kafka.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FreezerTemperatureProducer {

    @Value("${spring.kafka.streams.bootstrap-servers}")
    private String bootstrapServers;

    private KafkaTemplate<String, String> kafkaTemplate;

    public FreezerTemperatureProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(UUID id, Double temperature) {
        this.kafkaTemplate.send(Topic.FREEZER_TEMPERATURE, id.toString(), temperature.toString());
    }

}
