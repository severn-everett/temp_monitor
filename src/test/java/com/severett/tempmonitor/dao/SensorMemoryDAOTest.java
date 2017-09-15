package com.severett.tempmonitor.dao;

import com.severett.tempmonitor.exceptions.InvalidSensorException;
import com.severett.tempmonitor.model.TemperatureReading;
import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SensorMemoryDAOTest {

    private SensorDAO sensorDAO;
    
    @Before
    public void setup() {
        sensorDAO = new SensorDAOMemoryImpl();
    }
    
    @Test
    public void getTemperatureMetricsTest() {
        String sensorUuid = "sensor_uuid";
        sensorDAO.addTemperatureReading(sensorUuid, new TemperatureReading(Instant.now(), 10.0));
        sensorDAO.addTemperatureReading(sensorUuid, new TemperatureReading(Instant.now().minus(Duration.ofMinutes(5)), 30.0));
        sensorDAO.addTemperatureReading(sensorUuid, new TemperatureReading(Instant.now().minus(Duration.ofHours(5)), 35.0));
        sensorDAO.addTemperatureReading(sensorUuid, new TemperatureReading(Instant.now().minus(Duration.ofDays(10)), 32.0));
        sensorDAO.addTemperatureReading(sensorUuid, new TemperatureReading(Instant.now().minus(Duration.ofDays(40)), 36.0));
        try {
            Assert.assertEquals(2, sensorDAO.getLastHourTemperatureReadings(sensorUuid).size());
            Assert.assertEquals(3, sensorDAO.getLastWeekTemperatureReadings(sensorUuid).size());
            Assert.assertEquals(4, sensorDAO.getLastThirtyDaysTemperatureReadings(sensorUuid).size());
        } catch (InvalidSensorException ise) {
            Assert.fail(ise.getMessage());
        }
    }
    
    @Test
    public void invalidSensorUuidTest() {
        try {
            sensorDAO.getSensorEvents("non_existent");
            Assert.fail("Expected an InvalidSensorException to be raised");
        } catch (InvalidSensorException ise) {
            // No-op - expected behavior
        }
        
        try {
            sensorDAO.getLastHourTemperatureReadings("non_existent");
            Assert.fail("Expected an InvalidSensorException to be raised");
        } catch (InvalidSensorException ise) {
            // No-op - expected behavior
        }
    }
    
}
