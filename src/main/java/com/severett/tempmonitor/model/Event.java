package com.severett.tempmonitor.model;

public class Event {
    
    private final String type;
    private final TemperatureReading tempReading;
    
    public Event(String type, TemperatureReading tempReading) {
        this.type = type;
        this.tempReading = tempReading;
    }
    
    public Event(TemperatureReading tempReading) {
        this("TEMPERATURE_EXCEEDED", tempReading);
    }
    
    public String getType() {
        return type;
    }
    
    public TemperatureReading getTemperatureReading() {
        return tempReading;
    }
    
}
