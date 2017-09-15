package com.severett.tempmonitor.service;

import com.severett.tempmonitor.dao.SensorDAO;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import com.severett.tempmonitor.exceptions.InvalidSensorException;
import com.severett.tempmonitor.model.TemperatureReading;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorServiceTest {
    
    @Mock
    SensorDAO sensorDAO;
    
    SensorService sensorService;
    
    @Before
    public void setup() {
        sensorService = new SensorServiceImpl(sensorDAO);
    }
    
    @Test
    public void addTemperatureReadingsTest() {
        String sensorUuidOne = "sensor_uuid_one";
        String sensorUuidTwo = "sensor_uuid_two";
        
        sensorService.registerTemperature(sensorUuidOne, 50.0);
        sensorService.registerTemperature(sensorUuidTwo, 96.0);
        sensorService.registerTemperature(sensorUuidOne, 50.0);
        sensorService.registerTemperature(sensorUuidTwo, 98.0);
        sensorService.registerTemperature(sensorUuidTwo, 97.0);
        sensorService.registerTemperature(sensorUuidTwo, 96.0);
        sensorService.registerTemperature(sensorUuidTwo, 93.0);
        sensorService.registerTemperature(sensorUuidTwo, 98.0);
        sensorService.registerTemperature(sensorUuidTwo, 99.0);
        sensorService.registerTemperature(sensorUuidTwo, 100.0);
        
        verify(sensorDAO, times(2)).addTemperatureReading(eq(sensorUuidOne), anyObject());
        verify(sensorDAO, times(8)).addTemperatureReading(eq(sensorUuidTwo), anyObject());
        verify(sensorDAO, times(2)).addEvent(eq(sensorUuidTwo), anyObject());
    }
    
    public void getMetricsTest() {
        List<TemperatureReading> hourList = Arrays.asList(
                new TemperatureReading(Instant.now(), 30.0),
                new TemperatureReading(Instant.now(), 60.0),
                new TemperatureReading(Instant.now(), 90.0)
        );
        List<TemperatureReading> weekList = Arrays.asList(
                new TemperatureReading(Instant.now(), 30.0),
                new TemperatureReading(Instant.now(), 60.0),
                new TemperatureReading(Instant.now(), 90.0),
                new TemperatureReading(Instant.now(), 90.0),
                new TemperatureReading(Instant.now(), 90.0)
        );
        List<TemperatureReading> thirtyDaysList = Arrays.asList(
                new TemperatureReading(Instant.now(), 30.0),
                new TemperatureReading(Instant.now(), 60.0),
                new TemperatureReading(Instant.now(), 90.0),
                new TemperatureReading(Instant.now(), 90.0),
                new TemperatureReading(Instant.now(), 60.0),
                new TemperatureReading(Instant.now(), 45.0),
                new TemperatureReading(Instant.now(), 68.0),
                new TemperatureReading(Instant.now(), 92.0),
                new TemperatureReading(Instant.now(), 55.0)
        );
        try {
            given(sensorDAO.getLastHourTemperatureReadings(anyString())).willReturn(hourList);
            given(sensorDAO.getLastWeekTemperatureReadings(anyString())).willReturn(weekList);
            given(sensorDAO.getLastThirtyDaysTemperatureReadings(anyString())).willReturn(thirtyDaysList);
            Optional<SensorMetricsDto> sensorMetricsDTO = sensorService.getSensorMetrics("sensor_uuid");
            Assert.assertTrue(sensorMetricsDTO.isPresent());
            Assert.assertThat(sensorMetricsDTO.get().getAverageLastHour(), is(60.0));
            Assert.assertThat(sensorMetricsDTO.get().getAverageLast7Days(), is(72.0));
            Assert.assertThat(sensorMetricsDTO.get().getMaxLast30Days(), is(92.0));
        } catch (InvalidSensorException ise) {
            Assert.fail(ise.getMessage());
        }
        
    }
    
    @Test
    public void phantomMetricsTest() {
        try {
            given(sensorDAO.getLastHourTemperatureReadings(anyString())).willThrow(InvalidSensorException.class);
            given(sensorDAO.getLastWeekTemperatureReadings(anyString())).willThrow(InvalidSensorException.class);
            given(sensorDAO.getLastThirtyDaysTemperatureReadings(anyString())).willThrow(InvalidSensorException.class);
            Assert.assertFalse(sensorService.getSensorMetrics("invalid_sensor_uuid").isPresent());
        } catch (InvalidSensorException ise) {
            Assert.fail(ise.getMessage());
        }
    }
    
    @Test
    public void phantomEventsTest() {
        try {
            given(sensorDAO.getSensorEvents(anyString())).willThrow(InvalidSensorException.class);
            Assert.assertFalse(sensorService.getSensorEvents("invalid_sensor_uuid").isPresent());
        } catch (InvalidSensorException ise) {
            Assert.fail(ise.getMessage());
        }
        
    }
}
