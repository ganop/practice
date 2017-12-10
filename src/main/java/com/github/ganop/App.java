package com.github.ganop;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(new URI("https://www.google.co.uk/"));

        HttpPost httpPost = new HttpPost("https://cosy.geotogether.com/api/userapi/account/login");

        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("name", new JsonPrimitive(System.getenv("name")));
        json.add("password", new JsonPrimitive(System.getenv("password")));

        String payload = gson.toJson(json);
        StringEntity entity = new StringEntity(payload);
        httpPost.setEntity(entity);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, "application/json");
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
        headers.forEach(httpPost::setHeader);

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            JsonObject responsePayload = gson.fromJson(br, JsonObject.class);
            String token = responsePayload.getAsJsonPrimitive("token").getAsString();

            //entity2.writeTo(new BufferedOutputStream(System.out));
            System.out.println("token = " + token);
            EntityUtils.consume(responseEntity);
        }
    }
}