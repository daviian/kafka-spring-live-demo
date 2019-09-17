package cc.catalysts.kafka.livedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class LiveDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveDemoApplication.class, args);
    }

}
