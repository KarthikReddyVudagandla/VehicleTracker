package io.entity;

import java.io.Serializable;
import java.util.Objects;

public class ReadingsId implements Serializable {
    private String vin;
    private String timestamp;

    public ReadingsId() {
    }

    public ReadingsId(String vin, String timestamp) {
        this.vin = vin;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingsId rid = (ReadingsId) o;
        return vin.equals(rid.vin) &&
                timestamp.equals(rid.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin,timestamp);
    }
}
