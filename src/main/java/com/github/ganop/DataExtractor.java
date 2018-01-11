package com.github.ganop;

public interface DataExtractor {
    public boolean connect();

    public TemperatureEntry getData();

    public boolean disconnect();
}
