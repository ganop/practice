package com.github.ganop;

import com.github.ganop.data.TemperatureEntry;

public interface DataExtractor {
    public boolean connect();

    public TemperatureEntry getData();

    public boolean disconnect();
}
