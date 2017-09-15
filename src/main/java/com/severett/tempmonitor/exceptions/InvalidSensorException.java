package com.severett.tempmonitor.exceptions;

public class InvalidSensorException extends Exception {
    
    public InvalidSensorException(String sensorUuid) {
        super(String.format("Sensor '%s' not found", sensorUuid));
    }
    
}
