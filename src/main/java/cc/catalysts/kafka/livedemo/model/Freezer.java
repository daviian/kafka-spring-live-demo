package cc.catalysts.kafka.livedemo.model;

import java.util.UUID;

public class Freezer {

    private UUID id;

    private String name;

    private String location;

    public Freezer() {

    }

    public Freezer(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
