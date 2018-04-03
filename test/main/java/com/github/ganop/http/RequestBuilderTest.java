package com.github.ganop.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestBuilderTest {

    private RequestBuilder rb;

    @Before
    public void setUp() {
        this.rb = new RequestBuilder();
    }

    @Test
    public void withMethodGetReturnsGetRequest() {
        rb.withURL("http://www.google.com");
        rb.withMethod(HttpGet.METHOD_NAME);
        HttpUriRequest getRequest = rb.build();

        assertThat(getRequest.getMethod(), is("GET"));
        assertThat(getRequest instanceof HttpGet, is(true));
    }

    @Test
    public void withMethodPostReturnsPostRequest() {
        rb.withURL("http://www.google.com");
        rb.withMethod(HttpPost.METHOD_NAME);
        HttpUriRequest getRequest = rb.build();

        assertThat(getRequest.getMethod(), is("POST"));
        assertThat(getRequest instanceof HttpPost, is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void newRequestNullsAllFields() {
        rb.withURL("http://www.google.com");
        rb.withMethod(HttpPost.METHOD_NAME);

        rb.newRequest();

        rb.build();
    }

    @Test
    public void withEntity() {
    }

    @Test
    public void build() {
    }

    @Test
    public void withURL() {
    }
}