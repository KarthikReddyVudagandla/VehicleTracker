package io.controller;

import io.entity.alerts;
import io.repository.AlertsRepository;
import io.service.priority;
import io.service.rule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("IntegratedTest")
class AlertsControllerTest {

    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        alerts alert1 = new alerts();
        alert1.setRule(rule.EngineRpmHigh);
        alert1.setPriority(priority.HIGH);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.kkk'Z'");
        df.setTimeZone(tz);
        String time = df.format(new java.util.Date(System.currentTimeMillis()));
        alert1.setId("1");
        alert1.setTimestamp(time);
        alert1.setVin("1");
        alertsRepository.save(alert1);
    }

    @AfterEach
    void tearDown() {
        alertsRepository.deleteAll();
    }

    @Test
    void getHighAlerts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alerts/highAlerts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.is("HIGH")));
    }

    @Test
    void getVehicleAlerts() throws  Exception{
        mvc.perform(MockMvcRequestBuilders.get("/alerts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.is("HIGH")));
    }
}