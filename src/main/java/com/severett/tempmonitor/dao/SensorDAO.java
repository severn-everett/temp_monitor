package com.severett.tempmonitor.dao;

import com.severett.tempmonitor.exceptions.InvalidSensorException;
import com.severett.tempmonitor.model.Event;
import com.severett.tempmonitor.model.TemperatureReading;
import java.util.List;

public interface SensorDAO {
    
    void addTemperatureReading(String sensorUuid, TemperatureReading temperatureReading);
    
    void addEvent(String sensorUuid, Event event);
    
    List<Event> getSensorEvents(String sensorUuid) throws InvalidSensorException;
    
    List<TemperatureReading> getLastHourTemperatureReadings(String sensorUuid) throws InvalidSensorException;
    
    List<TemperatureReading> getLastWeekTemperatureReadings(String sensorUuid) throws InvalidSensorException;
    
    List<TemperatureReading> getLastThirtyDaysTemperatureReadings(String sensorUuid) throws InvalidSensorException;

}
