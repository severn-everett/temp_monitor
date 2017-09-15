package com.severett.tempmonitor;

import static org.hamcrest.Matchers.is;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = TempMonitorApplication.class)
@AutoConfigureMockMvc
public class TempMonitorApplicationTest {
    
    @Autowired
    private MockMvc mvc;

    @Test
    public void fullWorkflowTest() throws Exception {
        JSONObject putContent = new JSONObject();
        
        putContent.put("temperature", 92);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 96);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 97);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 98);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 99);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 94);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 95.5);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 97);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 99);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        putContent.put("temperature", 92);
        mvc.perform(put("/sensors/abc123/measurements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(putContent.toString()));
        
        mvc.perform(get("/sensors/abc123/metrics")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sensorUuid", is("abc123")))
            .andExpect(jsonPath("$.averageLastHour", is(95.95)))
            .andExpect(jsonPath("$.averageLast7Days", is(95.95)))
            .andExpect(jsonPath("$.maxLast30Days", is(99.0)));
        
        mvc.perform(get("/sensors/invalid_uuid/events")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
