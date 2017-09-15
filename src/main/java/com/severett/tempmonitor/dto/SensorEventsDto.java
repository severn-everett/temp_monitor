package com.severett.tempmonitor.dto;

import com.severett.tempmonitor.model.Event;
import java.util.List;
import java.util.stream.Collectors;

public final class SensorEventsDto {
    
    private final String sensorUuid;
    private final List<EventDto> events;
    
    public SensorEventsDto(String sensorUuid, List<Event> events) {
        this.sensorUuid = sensorUuid;
        this.events = events.stream().map(e -> new EventDto(e)).collect(Collectors.toList());
    }
    
    public String getSensorUuid() {
        return sensorUuid;
    }
    
    public List<EventDto> getEvents() {
        return events;
    }
    
}
