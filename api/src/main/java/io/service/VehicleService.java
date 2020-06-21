package io.service;

import io.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAll();
    void insert(List<Vehicle> vehicles);
}
