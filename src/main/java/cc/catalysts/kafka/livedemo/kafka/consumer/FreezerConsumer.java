package cc.catalysts.kafka.livedemo.kafka.consumer;

import cc.catalysts.kafka.livedemo.kafka.Topic;
import cc.catalysts.kafka.livedemo.model.Freezer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FreezerConsumer {

    private final Logger logger = LoggerFactory.getLogger(FreezerConsumer.class);

    private ObjectMapper mapper;

    public FreezerConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = Topic.FREEZER, groupId = "freezer-consumer")
    public void consume(String value) {
        if (value == null) {
            return;
        }

        try {
            Freezer freezer = mapper.readValue(value, Freezer.class);
            logger.info(String.format("A new freezer %s is standing in the %s", freezer.getName(), freezer.getLocation()));
        } catch (IOException e) {
            logger.error("Could not deserialize value", e);
        }
    }

}
