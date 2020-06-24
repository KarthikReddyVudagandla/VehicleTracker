package io.entity;

import io.service.priority;
import io.service.rule;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class alerts {
    @Id
    private String id;
    private String vin;
    private String timestamp;
    private priority priority;
    private rule rule;

    public alerts() {
        this.id = UUID.randomUUID().toString();
    }

    public alerts(String id, String vin, String timestamp, priority priority, rule rule) {
        this.id = UUID.randomUUID().toString();
        this.vin = vin;
        this.timestamp = timestamp;
        this.priority = priority;
        this.rule = rule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public io.service.priority getPriority() {
        return priority;
    }

    public void setPriority(io.service.priority priority) {
        this.priority = priority;
    }

    public io.service.rule getRule() {
        return rule;
    }

    public void setRule(io.service.rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "alerts{" +
                "id='" + id + '\'' +
                ", vin='" + vin + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", priority=" + priority +
                ", rule=" + rule +
                '}';
    }
}
