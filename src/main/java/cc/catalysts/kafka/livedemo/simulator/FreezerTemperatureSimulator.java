package cc.catalysts.kafka.livedemo.simulator;

import cc.catalysts.kafka.livedemo.kafka.producer.FreezerTemperatureProducer;
import cc.catalysts.kafka.livedemo.model.Freezer;
import cc.catalysts.kafka.livedemo.webservice.service.FreezerService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FreezerTemperatureSimulator {

    private FreezerTemperatureProducer temperatureProducer;

    private FreezerService freezerService;

    @Autowired
    public FreezerTemperatureSimulator(FreezerTemperatureProducer temperatureProducer, FreezerService freezerService) {
        this.temperatureProducer = temperatureProducer;
        this.freezerService = freezerService;
    }

    @Scheduled(fixedRate = 10000)
    public void simulateTemperatureMeasurement() {
        for (Freezer freezer : freezerService.getAll()) {
            temperatureProducer.publish(freezer.getId(), RandomUtils.nextDouble(2, 8));
        }
    }

}
