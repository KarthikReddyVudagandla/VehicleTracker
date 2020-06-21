package io.service;

import io.entity.Vehicle;
import io.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository repository;

    @Override
    public List<Vehicle> findAll() {
        return (List<Vehicle>) repository.findAll();
    }

    @Override
    @Transactional
    public void insert(List<Vehicle> vehicles) {
        repository.saveAll(vehicles);
    }
}
