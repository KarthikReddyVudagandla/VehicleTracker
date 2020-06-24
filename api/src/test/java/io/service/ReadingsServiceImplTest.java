package io.service;

import io.entity.GeoLocation;
import io.entity.Locations;
import io.entity.Readings;
import io.entity.Tires;
import io.repository.AlertsRepository;
import io.repository.ReadingsRepository;
import io.repository.TiresRepository;
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
class ReadingsServiceImplTest {
    @TestConfiguration
    static class ReadingsServiceConfig{

        @Bean
        public ReadingsService getService(){
            return new ReadingsServiceImpl();
        }
    }

    @Autowired
    ReadingsService readingsService;

    @MockBean
    private TiresRepository tiresRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private AlertsRepository alertsRepository;

    @MockBean
    private ReadingsRepository readingsRepository;

    List<Readings> list = new ArrayList();
    Readings readings1 = new Readings();
    Tires tires = new Tires();
//    Locations locations = new Locations();
//    List<Locations> locationsList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        readings1.setVin("1HGCR2F3XFA027534");
//        locations.setVin("1HGCR2F3XFA027534");
        readings1.setLatitude(41.803194);
//        locations.setLatitude(41.803194);
        readings1.setLongitude(-88.144406);
//        locations.setLongitude(-88.144406);
        readings1.setTimestamp("2017-05-25T17:31:25.268Z");
//        locations.setTimestamp("2017-05-25T17:31:25.268Z");
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
        readings1.setTires(tires);
        list.add(readings1);
//        locationsList.add(locations);
        Mockito.when(readingsRepository.findAll()).thenReturn(list);
        Mockito.when(readingsRepository.findByVin("1HGCR2F3XFA027534")).thenReturn(list);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Readings> readingsList = readingsService.findAll();
        assertEquals(list,readingsList,"Results must be same");
    }

//    @Test
//    void create() {
//    }

    @Test
    void findByVin() {
        List<Readings> readingsList = readingsService.findByVin("1HGCR2F3XFA027534");
        assertEquals(list,readingsList,"Results must be same");
    }

//    @Test
//    void findAllLocations() {
//        List<Locations> result = readingsService.findAllLocations();
//    }
}