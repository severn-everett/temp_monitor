package com.severett.tempmonitor.controller;

import com.severett.tempmonitor.dto.SensorEventsDto;
import com.severett.tempmonitor.dto.SensorMetricsDto;
import com.severett.tempmonitor.model.Event;
import com.severett.tempmonitor.model.TemperatureReading;
import com.severett.tempmonitor.service.SensorService;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SensorControllerTest.class)
public class SensorControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private SensorService sensorService;
    
    @Test
    public void uploadMeasurementTest() throws Exception {
        JSONObject putContent = new JSONObject();
        putContent.put("temperature", 10.0);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void noTemperatureTest() throws Exception {
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject().toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.reason", is("No value for temperature")));
    }
    
    @Test
    public void badTemperatureTest() throws Exception {
        JSONObject putContent = new JSONObject();
        putContent.put("temperature", "TEN");
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.reason", is("Value TEN at temperature of type java.lang.String cannot be converted to double")));
    }
    
    @Test
    public void getMetricsTest() throws Exception {
        SensorMetricsDto sensorMetrics = new SensorMetricsDto("abc123", 30.0, 40.0, 50.0);
        given(sensorService.getSensorMetrics(anyString()))
                .willReturn(Optional.of(sensorMetrics));
        mvc.perform(get("/sensors/abc123/metrics")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorUuid", is("abc123")))
                .andExpect(jsonPath("$.averageLastHour", is(30.0)))
                .andExpect(jsonPath("$.averageLast7Days", is(40.0)))
                .andExpect(jsonPath("$.maxLast30Days", is(50.0)));
    }
    
    @Test
    public void getEventsTest() throws Exception {
        Event twoDaysAgoEvent = new Event(new TemperatureReading(Instant.now().minus(Duration.ofDays(2)), 36.0));
        Event todayEvent = new Event(new TemperatureReading(Instant.now(), 37.0));
        SensorEventsDto sensorEvents = new SensorEventsDto("abc123", Arrays.asList(twoDaysAgoEvent, todayEvent));
        given(sensorService.getSensorEvents(anyString()))
                .willReturn(Optional.of(sensorEvents));
        mvc.perform(get("/sensors/abc123/events")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorUuid", is("abc123")))
                .andExpect(jsonPath("$.events[0].temperature", is(36.0)))
                .andExpect(jsonPath("$.events[1].temperature", is(37.0)));
    }
}
