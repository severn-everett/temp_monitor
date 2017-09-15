package com.severett.tempmonitor.dao;

import com.severett.tempmonitor.model.Event;
import com.severett.tempmonitor.model.TemperatureReading;
import java.util.List;

public interface SensorDAO {
    
    void addTemperatureReading(String sensorUuid, Double temperature);
    
    List<Event> getSensorEvents(String sensorUuid);
    
    List<TemperatureReading> getTemperatureReadings(String sensorUuid);

}
