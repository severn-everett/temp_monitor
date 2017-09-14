package com.severett.tempmonitor.controller;

import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    
    @RequestMapping(value="/{uuid}/measurements", method = RequestMethod.PUT)
    public void uploadMeasurement(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("uuid") String sensorUuid, @RequestParam("temperature") Double temperature) {
        
    }
    
    @RequestMapping(value="/{uuid}/metrics", method = RequestMethod.GET)
    public @ResponseBody SensorMetricsDto getSensorMetrics(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("uuid") String sensorUuid) {
        return null;
    }
    
    @RequestMapping(value="/{uuid}/metrics", method = RequestMethod.GET)
    public @ResponseBody SensorEventsDto getSensorEvents(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("uuid") String sensorUuid) {
        return null;
    }
    
}
