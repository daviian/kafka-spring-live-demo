package cc.catalysts.kafka.livedemo.webservice.service;

import cc.catalysts.kafka.livedemo.kafka.producer.FreezerProducer;
import cc.catalysts.kafka.livedemo.model.Freezer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class FreezerService {

    private FreezerProducer freezerProducer;

    @Autowired
    public FreezerService(FreezerProducer freezerProducer) {
        this.freezerProducer = freezerProducer;
    }

    public List<Freezer> getAll() {
        return Collections.emptyList();
    }

    public Freezer create(Freezer freezer) throws IOException {
        UUID id = UUID.randomUUID();
        freezer.setId(id);

        freezerProducer.publish(id, freezer);

        return freezer;
    }

    public void delete(UUID id) throws IOException {
        freezerProducer.publish(id, null);
    }

}
