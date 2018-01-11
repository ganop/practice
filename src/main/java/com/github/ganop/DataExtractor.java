package com.github.ganop;

public interface DataExtractor {
    public boolean connect();

    public Data getData();

    public boolean disconnect();
}
