package io.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.entity.Locations;
import io.entity.Readings;
import io.entity.alerts;

import java.util.List;

public interface ReadingsService {
    List<Readings> findAll();
    void create(Readings readings) throws JsonProcessingException;
    List<Readings> findByVin(String vin);

    List<Locations> findAllLocations();

}
