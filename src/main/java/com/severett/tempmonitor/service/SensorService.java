package com.severett.tempmonitor.service;

import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import java.util.Optional;

public interface SensorService {
    
    void registerTemperature(String sensorUuid, Double temperature);
    
    Optional<SensorMetricsDto> getSensorMetrics(String sensorUuid);
    
    Optional<SensorEventsDto> getSensorEvents(String sensorUuid);
    
}
