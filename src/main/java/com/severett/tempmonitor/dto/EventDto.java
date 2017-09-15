package com.severett.tempmonitor.dto;

import com.severett.tempmonitor.model.Event;
import java.time.Instant;

public class EventDto {
    
    private final Instant at;
    private final Double temperature;
    
    public EventDto(Event event) {
        this.at = event.getTemperatureReading().getAt();
        this.temperature = event.getTemperatureReading().getTemperature();
    }
    
    public Instant getAt() {
        return at;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
}
