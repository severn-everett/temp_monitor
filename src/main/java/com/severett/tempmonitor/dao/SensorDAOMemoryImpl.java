package com.severett.tempmonitor.dao;

import com.severett.tempmonitor.model.Event;
import com.severett.tempmonitor.model.TemperatureReading;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SensorDAOMemoryImpl implements SensorDAO {

    @Override
    public void addTemperatureReading(String sensorUuid, Double temperature) {
    }

    @Override
    public List<Event> getSensorEvents(String sensorUuid) {
        return Collections.emptyList();
    }

    @Override
    public List<TemperatureReading> getTemperatureReadings(String sensorUuid) {
        return Collections.emptyList();
    }
    
}
