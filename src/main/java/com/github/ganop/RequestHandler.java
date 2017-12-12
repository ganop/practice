package com.github.ganop;

import org.apache.http.client.methods.HttpGet;

public class RequestHandler {
    private final String method;
    private final String uri;

    public RequestHandler(String method, String uri) {

        this.method = method;
        this.uri = uri;
    }


    public HttpGet getRequest() {
        return new HttpGet();
    }
}
