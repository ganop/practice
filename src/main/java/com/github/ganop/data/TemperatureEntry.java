package com.github.ganop.data;

public class TemperatureEntry {
    private final String timestamp;
    private final float temperature;

    public TemperatureEntry(String timestamp, float temperature) {

        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "TemperatureEntry{" +
                "timestamp='" + timestamp + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
