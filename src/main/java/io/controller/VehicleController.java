package io.controller;

import io.entity.Vehicle;
import io.exception.BadRequestException;
import io.exception.ResourceNotFoundException;
import io.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "vehicles")
public class VehicleController {

    @Qualifier("vehicleServiceImpl")
    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> findAll(){
        return (List<Vehicle>) vehicleService.findAll();
    }

    @RequestMapping(value = "{vin}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle findByVin(@PathVariable("vin") String vin) throws ResourceNotFoundException {
        return vehicleService.findByVin( vin );
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insert(@RequestBody List<Vehicle> vehicles){
        vehicleService.insert(vehicles);
    }
}
