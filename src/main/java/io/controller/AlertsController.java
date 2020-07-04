package io.controller;

import io.entity.alerts;
import io.service.AlertsService;
import io.service.ReadingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "alerts")
public class AlertsController {

    @Qualifier("alertsServiceImpl")
    @Autowired
    private AlertsService alertsService;

    @RequestMapping(value = "highAlerts",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<alerts> getHighAlerts(){
        return alertsService.getHighAlerts();
    }

    @RequestMapping(value ="{vin}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<alerts> getVehicleAlerts(@PathVariable("vin") String vin){
        return alertsService.findByVin(vin);
    }
}
