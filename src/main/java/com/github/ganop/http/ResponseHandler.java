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
    private Gson gson;
    private JsonObject responsePayload;


    public ResponseHandler() {
        this.gson = new Gson();
        this.responsePayload = new JsonObject();
    }

    public Object handle(CloseableHttpResponse response, String type) {
        HttpEntity responseEntity = response.getEntity();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            setResponsePayload(gson.fromJson(br, JsonObject.class));

            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (type){
            case "token":
                return handleLoginResponse(responsePayload);
            case "data":
                return handleTempDataResponse(responsePayload);
            default:
                throw new IllegalArgumentException ("Unsupported response type:" + type);
        }


    }

    private TemperatureEntry handleTempDataResponse(JsonObject responsePayload) {
        String timestamp;
        timestamp = responsePayload.get("utc").getAsString();
        float temperature = responsePayload.getAsJsonArray("temperatureList").get(0).getAsFloat();
        return new TemperatureEntry(timestamp, temperature);
    }

    private String handleLoginResponse(JsonObject responsePayload) {
        return (responsePayload.getAsJsonPrimitive("token").getAsString());
    }

    private void setResponsePayload(JsonObject responsePayload) {
        this.responsePayload = responsePayload;
    }
}
