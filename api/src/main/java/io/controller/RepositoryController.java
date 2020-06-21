package io.controller;

import io.entity.Readings;
import io.entity.Vehicle;
import io.service.ReadingsService;
import io.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "readings")
public class RepositoryController {

    @Autowired
    private ReadingsService readingsService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Readings> findAll(){
        return (List<Readings>) readingsService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Readings readings){

        readingsService.create(readings);
    }
}
