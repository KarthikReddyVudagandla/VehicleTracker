package io.service;

import io.entity.Readings;
import io.entity.Vehicle;
import io.repository.ReadingsRepository;
import io.repository.TiresRepository;
import io.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReadingsServiceImpl implements ReadingsService {

    @Autowired
    ReadingsRepository readingsRepository;

    @Autowired
    TiresRepository tiresRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public List<Readings> findAll() {
        return (List<Readings>) readingsRepository.findAll();
    }

    @Override
    @Transactional
    public void create(Readings readings) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(readings.getVin());
        if(vehicle.isPresent()) { //save readings only if vin exists in vehicles tables
            //check for alerts here


            tiresRepository.save(readings.getTires());
            readingsRepository.save(readings);
        }
    }
}
