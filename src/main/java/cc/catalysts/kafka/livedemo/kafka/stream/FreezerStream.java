package cc.catalysts.kafka.livedemo.kafka.stream;

import cc.catalysts.kafka.livedemo.kafka.Topic;
import cc.catalysts.kafka.livedemo.model.Freezer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

@Service
public class FreezerStream {

    public static final String FREEZER_STORE = "freezer-store";

    private final StreamsBuilder kStreamBuilder;

    private Serde<String> stringSerde = new Serdes.StringSerde();
    private Serde<Freezer> jsonSerde = new JsonSerde<>(Freezer.class);

    public FreezerStream(@Qualifier("FreezerStreamsBuilder") StreamsBuilder kStreamBuilder) {
        this.kStreamBuilder = kStreamBuilder;
    }

    GlobalKTable<String, Freezer> describe() {
        return kStreamBuilder.globalTable(Topic.FREEZER, Materialized
                .<String, Freezer, KeyValueStore<Bytes, byte[]>>as(FREEZER_STORE)
                .withKeySerde(stringSerde)
                .withValueSerde(jsonSerde));
    }

}
