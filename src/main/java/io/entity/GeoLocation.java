package io.entity;

public class GeoLocation {
    private String vin;
    private String timestamp;
    private String formattedAddress;

    public GeoLocation() {
    }

    public GeoLocation(String vin, String timestamp, String formattedAddress) {
        this.vin = vin;
        this.timestamp = timestamp;
        this.formattedAddress = formattedAddress;
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

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "vin='" + vin + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                '}';
    }
}
