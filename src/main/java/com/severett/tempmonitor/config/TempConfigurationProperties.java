package com.severett.tempmonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class TempConfigurationProperties {
    
    private double tempThreshold = 95.0;
    private int eventRepeatThreshold = 3;
    
    public TempConfigurationProperties() {
    }
    
    public double getTempThreshold() {
        return tempThreshold;
    }
    
    public void setTempThreshold(double tempThreshold) {
        this.tempThreshold = tempThreshold;
    }
    
    public int getEventRepeatThreshold() {
        return eventRepeatThreshold;
    }
    
    public void setEventRepeatThreshold(int eventRepeatThreshold) {
        this.eventRepeatThreshold = eventRepeatThreshold;
    }
    
}
