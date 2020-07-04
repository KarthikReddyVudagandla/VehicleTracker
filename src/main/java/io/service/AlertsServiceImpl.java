package io.service;

import io.entity.alerts;
import io.repository.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Service
public class AlertsServiceImpl implements  AlertsService{

    @Autowired
    AlertsRepository alertsRepository;

    @Override
    public List<alerts> getHighAlerts() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.kkk'Z'");
        df.setTimeZone(tz);
        String time = df.format(new java.util.Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000)));
        return alertsRepository.getHighAlerts(priority.HIGH,time);
    }

    @Override
    public List<alerts> findByVin(String vin) {
        return alertsRepository.findByVin(vin);
    }
}
