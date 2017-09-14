package com.severett.tempmonitor.dto;

import com.severett.tempmonitor.model.Event;
import java.util.List;

public final class SensorEventsDto {
    
    private final String sensorUuid;
    private final List<Event> events;
    
    public SensorEventsDto(String sensorUuid, List<Event> events) {
        this.sensorUuid = sensorUuid;
        this.events = events;
    }
    
    public String getSensorUuid() {
        return sensorUuid;
    }
    
    public List<Event> getEvents() {
        return events;
    }
    
}
