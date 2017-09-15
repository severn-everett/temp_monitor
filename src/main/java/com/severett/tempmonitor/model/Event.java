package com.severett.tempmonitor.model;

import java.time.Instant;

public class Event {
    
    private final String type;
    private final Instant at;
    private final Double temperature;
    
    public Event(String type, Instant at, Double temperature) {
        this.type = type;
        this.at = at;
        this.temperature = temperature;
    }
    
    public Event(Instant at, Double temperature) {
        this("TEMPERATURE_EXCEEDED", at, temperature);
    }
    
    public String getType() {
        return type;
    }
    
    public Instant getAt() {
        return at;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
}
