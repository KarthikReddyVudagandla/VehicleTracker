package io.repository;

import io.entity.Readings;
import io.entity.ReadingsId;
import org.springframework.data.repository.CrudRepository;

public interface ReadingsRepository extends CrudRepository<Readings, ReadingsId> {
}
