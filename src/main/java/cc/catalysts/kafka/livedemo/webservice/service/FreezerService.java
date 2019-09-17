package cc.catalysts.kafka.livedemo.webservice.service;

import cc.catalysts.kafka.livedemo.kafka.producer.FreezerProducer;
import cc.catalysts.kafka.livedemo.kafka.stream.FreezerStream;
import cc.catalysts.kafka.livedemo.model.Freezer;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FreezerService {

    private FreezerProducer freezerProducer;

    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Autowired
    public FreezerService(FreezerProducer freezerProducer, @Qualifier("FreezerStreamsBuilder") StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.freezerProducer = freezerProducer;
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    public List<Freezer> getAll() {
        ReadOnlyKeyValueStore<String, Freezer> freezerStore = streamsBuilderFactoryBean.getKafkaStreams()
                .store(FreezerStream.FREEZER_STORE, QueryableStoreTypes.keyValueStore());

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(freezerStore.all(), Spliterator.ORDERED), false)
                .filter(kv -> kv.value != null)
                .map(kv -> kv.value)
                .collect(Collectors.toList());
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
