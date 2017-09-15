package com.severett.tempmonitor.dao;

import com.severett.tempmonitor.exceptions.InvalidSensorException;
import com.severett.tempmonitor.model.Event;
import com.severett.tempmonitor.model.TemperatureReading;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class SensorDAOMemoryImpl implements SensorDAO {
    
    private final Map<String, List<TemperatureReading>> tempReadingsMap;
    private final Map<String, List<Event>> eventsMap;
    
    public SensorDAOMemoryImpl() {
        tempReadingsMap = new ConcurrentHashMap<>();
        eventsMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addTemperatureReading(String sensorUuid, TemperatureReading temperatureReading) {
        tempReadingsMap.putIfAbsent(sensorUuid, new ArrayList<>());
        List<TemperatureReading> readingsSet = tempReadingsMap.get(sensorUuid);
        synchronized (readingsSet) {
            readingsSet.add(temperatureReading);
        }
    }
    
    @Override
    public void addEvent(String sensorUuid, Event event) {
        eventsMap.putIfAbsent(sensorUuid, new ArrayList<>());
        List<Event> eventsList = eventsMap.get(sensorUuid);
        synchronized (eventsList) {
            eventsList.add(event);
        }
    }

    @Override
    public List<Event> getSensorEvents(String sensorUuid) throws InvalidSensorException {
        if (eventsMap.containsKey(sensorUuid)) {
            List<Event> eventsList = eventsMap.get(sensorUuid);
            synchronized (eventsList) {
                return new ArrayList<>(eventsList);
            }
        } else {
            throw new InvalidSensorException(sensorUuid);
        }
    }

    @Override
    public List<TemperatureReading> getLastHourTemperatureReadings(String sensorUuid) throws InvalidSensorException {
        return getFilteredTemperatureReadings(sensorUuid, reading -> reading.getAt().isAfter(Instant.now().minus(Duration.ofHours(1))));
    }

    @Override
    public List<TemperatureReading> getLastWeekTemperatureReadings(String sensorUuid) throws InvalidSensorException {
        return getFilteredTemperatureReadings(sensorUuid, reading -> reading.getAt().isAfter(Instant.now().minus(Duration.ofDays(7))));
    }

    @Override
    public List<TemperatureReading> getLastThirtyDaysTemperatureReadings(String sensorUuid) throws InvalidSensorException {
        return getFilteredTemperatureReadings(sensorUuid, reading -> reading.getAt().isAfter(Instant.now().minus(Duration.ofDays(30))));
    }
    
    private List<TemperatureReading> getFilteredTemperatureReadings(String sensorUuid, Predicate<TemperatureReading> readingFilter) throws InvalidSensorException {
        if (tempReadingsMap.containsKey(sensorUuid)) {
            List<TemperatureReading> readingsList = tempReadingsMap.get(sensorUuid);
            synchronized (readingsList) {
                Calendar lastMonthCalendar = Calendar.getInstance();
                lastMonthCalendar.add(Calendar.MONTH, -1);
                return readingsList.stream()
                        .filter(readingFilter)
                        .collect(Collectors.toList());
            }
        } else {
            throw new InvalidSensorException(sensorUuid);
        }
    }
    
}
