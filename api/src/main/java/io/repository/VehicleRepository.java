package io.repository;

import io.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

}
