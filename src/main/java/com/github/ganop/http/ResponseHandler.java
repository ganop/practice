package com.github.ganop.http;

import com.github.ganop.data.TemperatureEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResponseHandler {
    public Object handle(CloseableHttpResponse response, String type) {
        HttpEntity responseEntity = response.getEntity();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject responsePayload = gson.fromJson(br, JsonObject.class);

        try {
            EntityUtils.consume(responseEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (type){
            case "token":
                String secToken = handleLoginResponse(responsePayload);
                return secToken;
            case "data":
                TemperatureEntry temp = handleTempDataResponse(responsePayload);
                return temp;
            default:
                throw new IllegalArgumentException ("Unsupported response type:" + type);
        }


    }

    private TemperatureEntry handleTempDataResponse(JsonObject responsePayload) {
        String timestamp = responsePayload.get("utc").getAsString();
        float temperature = responsePayload.getAsJsonArray("temperatureList").get(0).getAsFloat();
        TemperatureEntry temperatureEntry = new TemperatureEntry(timestamp, temperature);
        return temperatureEntry;
    }

    private String handleLoginResponse(JsonObject responsePayload) {
        return (responsePayload.getAsJsonPrimitive("token").getAsString());
    }
}
