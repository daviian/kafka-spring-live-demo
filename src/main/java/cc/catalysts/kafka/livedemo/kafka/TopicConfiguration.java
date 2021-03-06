package cc.catalysts.kafka.livedemo.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfiguration {

    @Value("${spring.kafka.streams.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic freezerTopic() {
        return new NewTopic(Topic.FREEZER, 1, (short) 1);
    }

    @Bean
    public NewTopic freezerTemperatureTopic() {
        return new NewTopic(Topic.FREEZER_TEMPERATURE, 1, (short) 1);
    }

    @Bean
    public NewTopic freezerTemperatureJoinTopic() {
        return new NewTopic(Topic.FREEZER_TEMPERATURE_JOIN, 1, (short) 1);
    }

    @Bean
    public NewTopic avgFreezerTemperatureTopic() {
        return new NewTopic(Topic.FREEZER_AVG_TEMPERATURE, 1, (short) 1);
    }

}
