package com.github.ganop;

public class App {

    public static void main(String[] args) {
        DataExtractor de = new CosyEndpointExtractor();
        PersistableData pd = de.getData();

        AppDao ad = new AppDao();
        ad.save(pd);
        //TODO Classic Producer-Consumer problem
        //Research solution

    }

}