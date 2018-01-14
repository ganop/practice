package com.github.ganop;

import com.github.ganop.data.TemperatureEntry;

public class App {

    public static void main(String[] args) {
        DataExtractor de = new CosyEndpointExtractor();
        if (de.connect()){
            TemperatureEntry temp = de.getData();
            System.out.println(temp.toString());
        }
//
//        AppDao ad = new AppDao();
//        ad.save(temp);
        //TODO Classic Producer-Consumer problem
        //Research solution

    }

}