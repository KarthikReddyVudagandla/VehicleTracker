package io.service;

import io.entity.Vehicle;
import io.exception.ResourceNotFoundException;
import io.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
class VehicleServiceImplTest {
    @TestConfiguration
    static class VehicleServiceConfig{

        @Bean
        public VehicleService getService(){
            return new VehicleServiceImpl();
        }
    }
    @Autowired
    VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    List<Vehicle> list = new ArrayList();
    Vehicle vehicle1 = new Vehicle();
    Vehicle vehicle2 = new Vehicle();

    @BeforeEach
    void setUp() {
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
        list.add(vehicle1);
        Mockito.when(vehicleRepository.findAll()).thenReturn(list);
        Mockito.when(vehicleRepository.findByVin("2")).thenReturn(java.util.Optional.ofNullable(vehicle2));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Vehicle>vehicles= vehicleService.findAll();
        assertEquals(list,vehicles,"result must be same");
    }

//    @Test
//    void insert() {
//    }

    @Test
    void findByVin() throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findByVin("2");
        assertEquals(vehicle2,vehicle,"Result must be same");
    }

    @Test
    void findByVinNotFound() {
        assertThrows(ResourceNotFoundException.class,()->vehicleService.findByVin("3"),"Exception  must be thrown");
    }
}