package com.github.ganop;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class AppTest {
    @Test
    public void requestHandlerCreatesGetRequestWhenGetMethodChosen() throws Exception {
        String method = "get";
        String uri = "https://www.google.co.uk/";

        RequestBuilder rh = new RequestBuilderBuilder().setMethod(method).setUri(uri).createRequestBuilder();
        HttpRequest request = rh.getRequest();

        assertThat(request, instanceOf(HttpGet.class));
    }

}