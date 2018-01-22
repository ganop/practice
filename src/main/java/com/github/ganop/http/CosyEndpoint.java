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

    public final String URL;
    public final String METHOD;
    public final Map<String, String> HEADERS;
    public final StringEntity ENTITY;


    CosyEndpoint(String type) {
        Map<String, String> prepHeaders;
        switch (type){
            case "login":
                this.URL = "https://cosy.geotogether.com/api/userapi/account/login";
                this.METHOD = HttpPost.METHOD_NAME;

                prepHeaders = new HashMap<>();
                prepHeaders.put(HttpHeaders.ACCEPT, "application/json");
                prepHeaders.put(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
                this.HEADERS =prepHeaders;


                JsonObject json = new JsonObject();
                json.add("name", new JsonPrimitive(System.getenv("name")));//TODO Need to allow for use of .properties
                json.add("password", new JsonPrimitive(System.getenv("password")));

                String payload = json.toString();
                this.ENTITY = new StringEntity(payload, Charsets.UTF_8);

                break;

            case "temp_data":
                this.URL = "https://cosy.geotogether.com/api/userapi/system/cosy-live-data/" + System.getenv("syscode");
                this.METHOD =HttpGet.METHOD_NAME;

                prepHeaders = new HashMap<>();
                this.HEADERS =prepHeaders;

                this.ENTITY =null;

                break;

            default:
                this.URL = null;
                this.ENTITY = null;
                this.METHOD = null;
                this.HEADERS = null;
        }
    }

    public void setSecurityToken(String token){
        if (token.contains(" ")){
            throw new IllegalArgumentException("Token was not a valid security token string");
        }
        this.HEADERS.put(HttpHeaders.AUTHORIZATION,"Bearer " + token);
    }
}
