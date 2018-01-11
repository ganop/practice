package com.github.ganop;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.util.Map;

public class RequestBuilder {
    private final String uri;

    public RequestBuilder(String uri) {
        this.uri = uri;
    }

    public RequestBuilder withMethod(String method) {
        return null;
    }

    public RequestBuilder withHeaders(Map<String, String> headers) {
        return null;
    }

    public RequestBuilder withEntity(StringEntity entity) {
        return null;
    }

    public HttpUriRequest build() {
        return null;
    }
}
