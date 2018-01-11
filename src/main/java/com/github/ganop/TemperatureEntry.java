package com.github.ganop;

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
}
