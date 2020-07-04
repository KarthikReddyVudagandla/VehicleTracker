package io.repository;

import io.entity.alerts;
import io.service.priority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Priority;
import java.util.List;

public interface AlertsRepository extends CrudRepository<alerts, String > {
    @Query("SELECT alert FROM alerts alert WHERE alert.priority=:p AND alert.timestamp>:time ORDER BY alert.timestamp DESC ")
    List<alerts> getHighAlerts(@Param("p") priority p,@Param("time")String time);

    List<alerts> findByVin(String vin);
}
