package io.service;

import io.entity.alerts;

import java.util.List;

public interface AlertsService {
    List<alerts> getHighAlerts();
    List<alerts> findByVin(String vin);
}
