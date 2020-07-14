package io.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.entity.Locations;
import io.entity.Readings;
import io.entity.Vehicle;
import io.entity.alerts;
import io.repository.AlertsRepository;
import io.repository.ReadingsRepository;
import io.repository.TiresRepository;
import io.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

;

@Service
public class ReadingsServiceImpl implements ReadingsService {

    @Autowired
    ReadingsRepository readingsRepository;

    @Autowired
    TiresRepository tiresRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    AlertsRepository alertsRepository;

    @Autowired
    SnsService snsService;

    @Override
    public List<Readings> findAll() {
        return (List<Readings>) readingsRepository.findAll();
    }

    @Override
    @Transactional
    public void create(Readings readings) throws JsonProcessingException {
        Optional<Vehicle> vehicle = vehicleRepository.findById(readings.getVin());
        ObjectMapper objectMapper = new ObjectMapper();
        if(vehicle.isPresent()) { //save readings only if vin exists in vehicles tables
            //check for alerts here
            if(readings.getEngineRpm() > vehicle.get().getRedlineRpm()){
                alerts alert = new alerts();
                alert.setVin(vehicle.get().getVin());
                alert.setTimestamp(readings.getTimestamp());
                alert.setPriority(priority.HIGH);
                alert.setRule(rule.EngineRpmHigh);
                alertsRepository.save(alert);
                snsService.send("Engine RPM Alert",objectMapper.writeValueAsString(alert));
                System.out.println("ALERT: "+alert);
            }
            if(readings.isCheckEngineLightOn() || readings.isEngineCoolantLow()){
                alerts alert = new alerts();
                alert.setVin(vehicle.get().getVin());
                alert.setTimestamp(readings.getTimestamp());
                alert.setPriority(priority.LOW);
                alert.setRule(rule.EngineCoolantLow_OR_CheckEngineLightOn);
                alertsRepository.save(alert);
                snsService.send("Engine Check or Coolant Alert",objectMapper.writeValueAsString(alert));
                System.out.println("ALERT: "+alert);
            }
            if(readings.getFuelVolume() < (0.1*vehicle.get().getMaxFuelVolume())){
                alerts alert = new alerts();
                alert.setVin(vehicle.get().getVin());
                alert.setTimestamp(readings.getTimestamp());
                alert.setPriority(priority.MEDIUM);
                alert.setRule(rule.FuelVolumeLow);
                alertsRepository.save(alert);
                snsService.send("Fuel Alert",objectMapper.writeValueAsString(alert));
                System.out.println("ALERT: "+alert);
            }
            if(readings.getTires().getFrontLeft() < 32 || readings.getTires().getFrontLeft() > 36
                    || readings.getTires().getFrontRight() < 32 || readings.getTires().getFrontRight() > 36
                    || readings.getTires().getRearLeft() < 32 || readings.getTires().getRearLeft() > 36
                    || readings.getTires().getRearRight() < 32 || readings.getTires().getRearRight() > 36){
                alerts alert = new alerts();
                alert.setVin(vehicle.get().getVin());
                alert.setTimestamp(readings.getTimestamp());
                alert.setPriority(priority.LOW);
                alert.setRule(rule.TirePressureAbnormal);
                alertsRepository.save(alert);
                snsService.send("Tire Pressure Alert",objectMapper.writeValueAsString(alert));
                System.out.println("ALERT: "+alert);
            }

            tiresRepository.save(readings.getTires());
            readingsRepository.save(readings);
        }
    }

    @Override
    public List<Readings> findByVin(String vin) {
        return readingsRepository.findByVin(vin);
    }

    @Override
    public List<Locations> findAllLocations() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.kkk'Z'");
        df.setTimeZone(tz);
        String time = df.format(new java.util.Date(System.currentTimeMillis() - (1 * 30 * 60 * 1000)));
        return readingsRepository.findAllLocations(time);
    }

}
