package cc.catalysts.kafka.livedemo.model;

import java.util.UUID;

public class FreezerTemperature {

    private UUID id;
    private String name;
    private String location;
    private Double temperature;

    public FreezerTemperature() {

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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
