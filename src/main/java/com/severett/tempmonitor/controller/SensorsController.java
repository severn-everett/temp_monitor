package com.severett.tempmonitor.controller;

import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import com.severett.tempmonitor.service.SensorService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorsController.class);
    
    private static final String SUCCESS_KEY = "success";
    
    private final SensorService sensorService;
    
    @Autowired
    public SensorsController(SensorService sensorService) {
        this.sensorService = sensorService;
    }
    
    @RequestMapping(value="/{uuid}/measurements", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> uploadMeasurement(@PathVariable("uuid") String sensorUuid, @RequestBody String requestBody) {
        LOGGER.debug("Measurement upload received for sensor {}", sensorUuid);
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> responseMap = new HashMap<>();
        try {
            JSONObject requestObj = new JSONObject(requestBody);
            headers.add("Content-Type", "application/json");
            sensorService.registerTemperature(sensorUuid, requestObj.getDouble("temperature"));
            responseMap.put(SUCCESS_KEY, true);
            return new ResponseEntity<>(responseMap, headers, HttpStatus.OK);
        } catch (JSONException jsone) {
            LOGGER.error("Error uploading measurement for sensor {}", jsone.getMessage());
            responseMap.put(SUCCESS_KEY, false);
            responseMap.put("reason", jsone.getMessage());
            return new ResponseEntity<>(responseMap, headers, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value="/{uuid}/metrics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SensorMetricsDto> getSensorMetrics(@PathVariable("uuid") String sensorUuid) {
        LOGGER.debug("Received metrics request for sensor {}", sensorUuid);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json"); 
        Optional<SensorMetricsDto> sensorMetrics = sensorService.getSensorMetrics(sensorUuid);
        if (sensorMetrics.isPresent()) {
            return new ResponseEntity<>(sensorMetrics.get(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value="/{uuid}/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SensorEventsDto> getSensorEvents(@PathVariable("uuid") String sensorUuid) {
        LOGGER.debug("Received events request for sensor {}", sensorUuid);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Optional<SensorEventsDto> sensorEvents = sensorService.getSensorEvents(sensorUuid);
        if (sensorEvents.isPresent()) {
            return new ResponseEntity<>(sensorEvents.get(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
    
}
