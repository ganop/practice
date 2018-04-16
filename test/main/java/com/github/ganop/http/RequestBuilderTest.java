package com.github.ganop.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
    public void withHeadersCannotBeCalledBeforeUrlOrMethod() {
        fail();
    }

    @Test
    public void withHeadersSetsRequiredHeaders() {
        fail();
    }
    @Test
    public void withHeadersWorksWhenEmpty() {
        fail();
    }

    @Test
    public void withHeadersWorksWhenNull() {
        fail();
    }

    @Test
    public void buildSetsNoHeadersWhenHeadersMethodNotCalled() {
        fail();
    }

    @Test
    public void withEntityOnlyAllowedForPost() {
        fail();
    }

    @Test
    public void withEntityCannotBeCalledBeforeUrlAndMethod() {
        fail();
    }

    @Test
    public void withEntitySetsRequiredEntity() {
        fail();
    }

    @Test
    public void build() {
        //TODO test cases for the build() Method;
    }

    @Test
    public void withURLSetsRequiredUrlOnBuilder() {
        fail();
    }

    @Test
    public void withURLSetsRequiredUrlOnRequest() {
        fail();
    }

}