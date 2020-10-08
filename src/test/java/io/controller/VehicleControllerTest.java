package io.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.entity.Vehicle;
import io.repository.VehicleRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(JUnitPlatform.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("IntegratedTest")
class VehicleControllerTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        List<Vehicle> list = new ArrayList();
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        vehicle1.setVin("1");
        vehicle1.setMake("A");
        vehicle1.setLastServiceDate("1");
        vehicle1.setMaxFuelVolume(10);
        vehicle1.setModel("AA");
        vehicle1.setRedlineRpm(10);
        vehicle1.setYear(2020);
        list.add(vehicle1);
        vehicle2.setVin("2");
        vehicle2.setMake("A");
        vehicle2.setLastServiceDate("1");
        vehicle2.setMaxFuelVolume(10);
        vehicle2.setModel("AA");
        vehicle2.setRedlineRpm(10);
        vehicle2.setYear(2020);
        list.add(vehicle2);
        vehicleRepository.saveAll(list);
    }

    @AfterEach
    void tearDown() {
        vehicleRepository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1")));
    }

    @Test
    void findByVin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1")));
    }
    @Test
    void findByVinNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void insert() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Vehicle> vehicleList = new ArrayList<>();
        Vehicle vehicle = new Vehicle();
        vehicle.setVin("4");
        vehicle.setMake("A");
        vehicle.setLastServiceDate("1");
        vehicle.setMaxFuelVolume(10);
        vehicle.setModel("AA");
        vehicle.setRedlineRpm(10);
        vehicle.setYear(2020);
        vehicleList.add(vehicle);
        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(vehicleList)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));

    }
}