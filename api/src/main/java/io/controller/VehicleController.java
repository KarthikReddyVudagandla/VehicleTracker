package io.controller;

import io.entity.Vehicle;
import io.repository.VehicleRepository;
import io.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "vehicles")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> findAll(){
        return (List<Vehicle>) service.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insert(@RequestBody List<Vehicle> vehicles){

        service.insert(vehicles);
    }
}
