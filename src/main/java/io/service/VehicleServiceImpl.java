package io.service;

import io.entity.Vehicle;
import io.exception.ResourceNotFoundException;
import io.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findAll() {
        return (List<Vehicle>) vehicleRepository.findAll();
    }

    @Override
    @Transactional
    public void insert(List<Vehicle> vehicles) {
        vehicleRepository.saveAll(vehicles);
    }

    @Override
    public Vehicle findByVin(String vin) throws ResourceNotFoundException{
        Optional<Vehicle> vehicle = vehicleRepository.findByVin(vin);
        if(!vehicle.isPresent()){
            throw new ResourceNotFoundException("Vehicle with vin: "+vin+" doesn't exist");
        }
        return vehicle.get();
    }
}
