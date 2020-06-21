package io.service;

import io.entity.Readings;

import java.util.List;

public interface ReadingsService {
    List<Readings> findAll();
    void create(Readings readings);
}
