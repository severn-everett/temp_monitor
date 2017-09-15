package com.severett.tempmonitor.model;

import java.time.Instant;

public class TemperatureReading {

    private final Instant at;
    private final Double temperature;
    
    public TemperatureReading(Instant at, Double temperature) {
        this.at = at;
        this.temperature = temperature;
    }
    
    public Instant getAt() {
        return at;
    }
    
    public Double getTemperature() {
        return temperature;
    }
}
