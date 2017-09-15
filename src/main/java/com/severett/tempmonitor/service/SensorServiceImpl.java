package com.severett.tempmonitor.service;

import com.severett.tempmonitor.dao.SensorDAO;
import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    SensorDAO sensorDao;
    
    @Override
    public void registerTemperature(String sensorUuid, Double temperature) {
        sensorDao.addTemperatureReading(sensorUuid, temperature);
    }

    @Override
    public Optional<SensorMetricsDto> getSensorMetrics(String sensorUuid) {
        return Optional.empty();
    }

    @Override
    public Optional<SensorEventsDto> getSensorEvents(String sensorUuid) {
        return Optional.empty();
    }
    
}
