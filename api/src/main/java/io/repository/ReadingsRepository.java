package io.repository;

import io.entity.Locations;
import io.entity.Readings;
import io.entity.ReadingsId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadingsRepository extends CrudRepository<Readings, ReadingsId> {
    List<Readings> findByVin(String vin);

    @Query("SELECT new io.entity.Locations(readings.vin,readings.timestamp,readings.latitude,readings.longitude) FROM Readings readings WHERE readings.timestamp >:time ORDER BY readings.vin")
    List<Locations> findAllLocations(@Param("time")String time);
}
