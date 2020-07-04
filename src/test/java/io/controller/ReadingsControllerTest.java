package io.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.entity.Readings;
import io.entity.Tires;
import io.repository.AlertsRepository;
import io.repository.ReadingsRepository;
import io.repository.TiresRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("IntegratedTest")
class ReadingsControllerTest {

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private TiresRepository tiresRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {

        Readings readings1 = new Readings();
        Tires tires = new Tires();
        readings1.setVin("1HGCR2F3XFA027534");
        readings1.setLatitude(41.803194);
        readings1.setLongitude(-88.144406);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.kkk'Z'");
        df.setTimeZone(tz);
        String time = df.format(new java.util.Date(System.currentTimeMillis()));
        readings1.setTimestamp(time);
        readings1.setFuelVolume(1.5);
        readings1.setSpeed(85);
        readings1.setEngineHp(240);
        readings1.setCheckEngineLightOn(false);
        readings1.setEngineCoolantLow(true);
        readings1.setCruiseControlOn(true);
        readings1.setEngineRpm(6300);
        tires.setFrontLeft(34);
        tires.setFrontRight(36);
        tires.setRearLeft(29);
        tires.setRearRight(34);
        tiresRepository.save(tires);
        readings1.setTires(tires);
        readingsRepository.save(readings1);

    }

    @AfterEach
    void tearDown() {
       // tiresRepository.deleteAll();
        readingsRepository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/readings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    void findByVin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/readings/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")));
    }

    @Test
    void create() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Readings readings1 = new Readings();
        Tires tires = new Tires();
        readings1.setVin("1HGCR2F3XFA027534");
        readings1.setLatitude(-41.803194);
        readings1.setLongitude(88.144406);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.kkk'Z'");
        df.setTimeZone(tz);
        String time = df.format(new java.util.Date(System.currentTimeMillis()));
        readings1.setTimestamp(time);
        readings1.setFuelVolume(1.5);
        readings1.setSpeed(85);
        readings1.setEngineHp(240);
        readings1.setCheckEngineLightOn(false);
        readings1.setEngineCoolantLow(true);
        readings1.setCruiseControlOn(true);
        readings1.setEngineRpm(6300);
        tires.setFrontLeft(34);
        tires.setFrontRight(36);
        tires.setRearLeft(29);
        tires.setRearRight(34);
        tiresRepository.save(tires);
        mvc.perform(MockMvcRequestBuilders.post("/readings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(readings1)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findAllLocations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/readings/locations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].formattedAddress", Matchers.is("40 Shuman Blvd, Naperville, IL 60563, USA")));
    }
}