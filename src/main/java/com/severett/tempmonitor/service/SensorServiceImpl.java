package com.severett.tempmonitor.service;

import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {

    @Override
    public void registerTemperature(String sensorUuid, Double temperature) {
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
