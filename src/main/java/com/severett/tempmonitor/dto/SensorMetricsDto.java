package com.severett.tempmonitor.dto;

public final class SensorMetricsDto {
    
    private final String sensorUuid;
    private final Double averageLastHour;
    private final Double averageLast7Days;
    private final Double maxLast30Days;
    
    public SensorMetricsDto(String sensorUuid, Double averageLastHour, Double averageLast7Days, Double maxLast30Days) {
        this.sensorUuid = sensorUuid;
        this.averageLastHour = averageLastHour;
        this.averageLast7Days = averageLast7Days;
        this.maxLast30Days = maxLast30Days;
    }
    
    public String getSensorUuid() {
        return sensorUuid;
    }
    
    public Double getAverageLastHour() {
        return averageLastHour;
    }
    
    public Double getAverageLast7Days() {
        return averageLast7Days;
    }
    
    public Double getMaxLast30Days() {
        return maxLast30Days;
    }
    
}
