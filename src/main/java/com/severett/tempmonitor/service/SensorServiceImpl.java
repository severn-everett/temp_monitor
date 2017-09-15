package com.severett.tempmonitor.service;

import com.severett.tempmonitor.config.TempConfigurationProperties;
import com.severett.tempmonitor.dao.SensorDAO;
import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import com.severett.tempmonitor.exceptions.InvalidSensorException;
import com.severett.tempmonitor.model.Event;
import com.severett.tempmonitor.model.TemperatureReading;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {
    
    private final Map<String, List<TemperatureReading>> excedeTempCountMap;
    
    private final SensorDAO sensorDao;
    private final TempConfigurationProperties tempProperties;
    
    @Autowired
    public SensorServiceImpl(SensorDAO sensorDAO, TempConfigurationProperties tempProperties) {
        this.sensorDao = sensorDAO;
        this.tempProperties = tempProperties;
        this.excedeTempCountMap = new ConcurrentHashMap<>();
    }
    
    @Override
    public void registerTemperature(String sensorUuid, Double temperature) {
        TemperatureReading tempReading = new TemperatureReading(Instant.now(), temperature);
        sensorDao.addTemperatureReading(sensorUuid, tempReading);
        if (tempReading.getTemperature().compareTo(tempProperties.getTempThreshold()) > 0) {
            excedeTempCountMap.putIfAbsent(sensorUuid, new LinkedList<>());
            List<TemperatureReading> excedeTempList = excedeTempCountMap.get(sensorUuid);
            synchronized (excedeTempList) {
                excedeTempList.add(tempReading);
                if (excedeTempList.size() == tempProperties.getEventRepeatThreshold()) {
                    sensorDao.addEvent(sensorUuid, new Event(excedeTempList.get(0)));
                }
            }
        } else {
            Optional<List<TemperatureReading>> excedeTempList = Optional.ofNullable(excedeTempCountMap.getOrDefault(sensorUuid, null));
            excedeTempList.ifPresent(tempList -> {
                synchronized (tempList) {
                    tempList.clear();
                }
            });
        }
    }

    @Override
    public Optional<SensorMetricsDto> getSensorMetrics(String sensorUuid) {
        try {
            Double avgLastHour = sensorDao.getLastHourTemperatureReadings(sensorUuid)
                    .stream()
                    .mapToDouble(TemperatureReading::getTemperature)
                    .average()
                    .orElse(Double.NaN);
            Double avgLastWeek = sensorDao.getLastWeekTemperatureReadings(sensorUuid)
                    .stream()
                    .mapToDouble(TemperatureReading::getTemperature)
                    .average()
                    .orElse(Double.NaN);
            Double maxTemp = sensorDao.getLastThirtyDaysTemperatureReadings(sensorUuid)
                    .stream()
                    .mapToDouble(TemperatureReading::getTemperature)
                    .max()
                    .orElse(Double.NaN);
            return Optional.of(new SensorMetricsDto(sensorUuid, avgLastHour, avgLastWeek, maxTemp));
        } catch (InvalidSensorException ise) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SensorEventsDto> getSensorEvents(String sensorUuid) {
        try {
            return Optional.of(new SensorEventsDto(sensorUuid, sensorDao.getSensorEvents(sensorUuid)));
        } catch (InvalidSensorException ise) {
            return Optional.empty();
        }
    }
    
}
