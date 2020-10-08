package io.repository;

import io.entity.Tires;
import org.springframework.data.repository.CrudRepository;

public interface TiresRepository extends CrudRepository<Tires, String> {
}
