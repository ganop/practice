package com.github.ganop.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.Map;

public class RequestBuilder {
    private String uri;
    private String method;
    private byte step;
    private ArrayList<Header> headers;
    private HttpEntity entity;


    public RequestBuilder() {
        this.step=1;
        this.headers = new ArrayList<>();

    }

    public RequestBuilder withMethod(String method) {
        if (step != 2) {
            throw new IllegalStateException("Method called too early. URL not set.");
        }
        this.method = method;
        step++;
        return this;
    }

    public RequestBuilder withHeaders(Map<String, String> headers) {
        if (step < 3){
            throw new IllegalStateException("Headers assigned too early. Request method not set.");
        }

        headers.forEach((k, v) -> this.headers.add(new BasicHeader(k, v)));
        this.step++;
        return this;
    }

    public RequestBuilder withEntity(StringEntity entity) {
        if (step < 3){
            throw new IllegalStateException("Headers assigned too early. Request method not set.");
        }

        this.entity = entity;
        this.step++;

        return this;
    }

    public HttpUriRequest build() {
        if (this.uri == null){
            throw new IllegalStateException("URI has not been set. Call 'withUri' method first");
        }
        if (this.step < 3) {
            throw new IllegalStateException("URI has not been set. Call 'withUri' method first");
        }

        switch (this.method) {
            case "GET":
                HttpGet httpGet = new HttpGet(this.uri);
                httpGet.setHeaders(headers.toArray(new Header[0]));
                return httpGet;
            case "POST":
                HttpPost httpPost = new HttpPost(this.uri);
                httpPost.setHeaders(headers.toArray(new Header[0]));
                httpPost.setEntity(entity);
                return httpPost;
            default:
                throw new IllegalArgumentException("Request method: " + this.method + "not supported");
        }

    }

    public RequestBuilder withURL(String url) {
        this.uri = url;
        this.step++;
        return this;
    }

    public RequestBuilder newRequest() {
        this.uri=null;
        this.method=null;
        this.step=1;

        return this;
    }
}
