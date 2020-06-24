package io.service;

import io.entity.Vehicle;
import io.exception.BadRequestException;
import io.exception.ResourceNotFoundException;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAll();
    void insert(List<Vehicle> vehicles);

    Vehicle findByVin(String vin) throws ResourceNotFoundException;
}
