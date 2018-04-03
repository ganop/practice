package com.github.ganop.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.util.HashMap;
import java.util.Map;

public enum CosyEndpoint {
    LOGIN_ENDPOINT("login"),
    TEMP_DATA_ENDPOINT("temp_data");

    private final String url;
    private final String method;
    private final Map<String, String> headers;
    private final StringEntity entity;


    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public StringEntity getEntity() {
        return entity;
    }


    CosyEndpoint(String type) {
        Map<String, String> prepHeaders;
        switch (type){
            case "login":
                this.url = "https://cosy.geotogether.com/api/userapi/account/login";
                this.method = HttpPost.METHOD_NAME;

                prepHeaders = new HashMap<>();
                prepHeaders.put(HttpHeaders.ACCEPT, "application/json");
                prepHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
                this.headers =prepHeaders;


                JsonObject json = new JsonObject();
                json.add("name", new JsonPrimitive(System.getenv("name")));//TODO Need to allow for use of .properties
                json.add("password", new JsonPrimitive(System.getenv("password")));

                String payload = json.toString();
                this.entity = new StringEntity(payload, Charsets.UTF_8);

                break;

            case "temp_data":
                this.url = "https://cosy.geotogether.com/api/userapi/system/cosy-live-data/" + System.getenv("syscode");
                this.method =HttpGet.METHOD_NAME;

                prepHeaders = new HashMap<>();
                this.headers =prepHeaders;

                this.entity =null;

                break;

            default:
                this.url = null;
                this.entity = null;
                this.method = null;
                this.headers = null;
        }
    }

    public void setSecurityToken(String token){
        if (token.contains(" ")){
            throw new IllegalArgumentException("Token was not a valid security token string");
        }
        this.getHeaders().put(HttpHeaders.AUTHORIZATION,"Bearer " + token);
    }
}
