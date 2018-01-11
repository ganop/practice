package com.github.ganop;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CosyEndpointExtractor implements DataExtractor {
    private static final String COSY_DATA_ENDPOINT = "https://cosy.geotogether.com/api/userapi/system/cosy-live-data/";
    private String token;
    private static final String COSY_ENDPOINT_URL = "https://cosy.geotogether.com/api/userapi/account/login";


    private HttpUriRequest prepareRequest(String url, String method) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");

        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("name", new JsonPrimitive(System.getenv("name")));
        json.add("password", new JsonPrimitive(System.getenv("password")));

        String payload = gson.toJson(json);
        StringEntity entity = new StringEntity(payload, Charsets.UTF_8);

        RequestBuilder builder = new RequestBuilder(url);
        builder.withMethod(method);
        builder.withHeaders(headers);
        builder.withEntity(entity);

        return builder.build();
    }

    @Override
    public boolean connect() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpUriRequest loginRequest = prepareRequest(COSY_ENDPOINT_URL, "post");
        try (CloseableHttpResponse response = httpclient.execute(loginRequest)) {
            HttpEntity responseEntity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(responseEntity.getContent()));

            Gson gson = new Gson();
            JsonObject responsePayload = gson.fromJson(br, JsonObject.class);

            setToken(responsePayload.getAsJsonPrimitive("token").getAsString());

            EntityUtils.consume(responseEntity);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TemperatureEntry getData() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest dataRequest = prepareRequest(COSY_DATA_ENDPOINT, "get");
        try (CloseableHttpResponse response = httpClient.execute(dataRequest)){
            HttpEntity responseEntity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(responseEntity.getContent()));

            Gson gson = new Gson();
            JsonObject responsePayload = gson.fromJson(br, JsonObject.class);
            String timestamp = responsePayload.get("utc").getAsString();
            float temperature = responsePayload.getAsJsonArray("temperatureList").get(0).getAsFloat();
            TemperatureEntry temperatureEntry = new TemperatureEntry(timestamp, temperature);

            EntityUtils.consume(responseEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean disconnect() {
        return false;
    }

    private void setToken(String token) {
        this.token = token;
    }

    private String getToken (){
        return this.token;
    }
}
