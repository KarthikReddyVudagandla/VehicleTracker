package io.service;

import io.entity.Locations;
import io.entity.Readings;
import io.entity.alerts;

import java.util.List;

public interface ReadingsService {
    List<Readings> findAll();
    void create(Readings readings);
    List<Readings> findByVin(String vin);

    List<Locations> findAllLocations();

}
