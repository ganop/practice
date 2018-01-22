package com.github.ganop.http;

import org.apache.http.HttpHeaders;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CosyEndpointTest {
    private CosyEndpoint testEndpoint = CosyEndpoint.TEMP_DATA_ENDPOINT;

    @After
    public void cleanUp(){
        assert testEndpoint.HEADERS != null;
        testEndpoint.HEADERS.clear();
    }

    @Test
    public void setTokenMethodSetsValidAuthorizationString() {
        String authorization = "ASHFNASknISNXO09092uhrqha9donb3";
        testEndpoint.setSecurityToken(authorization);

        assert testEndpoint.HEADERS != null;
        assertThat(testEndpoint.HEADERS.keySet(), hasItem(HttpHeaders.AUTHORIZATION));
        assertThat(testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION), is("Bearer " + authorization));
    }

    @Test
    public void setTokenMethodOverwritesPreviousToken() {
        String token1 = "ASHFNASknISNXO09092uhrqha9donb3";
        String token2 = "ASJOlUAmHEFO79ATERGb76aert967";

        testEndpoint.setSecurityToken(token1);
        String authorization1 = testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION);
        testEndpoint.setSecurityToken(token2);
        String authorization2 = testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION);

        assertThat(authorization2, not(authorization1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void setTokenMethodDoesNotAcceptSpacesInToken() {
        String token1 = "ASHFNASknISNXO09092 uhrqha9donb3";

        testEndpoint.setSecurityToken(token1);
        String authorization1 = testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void noAuthorizationHeaderExistsIfTokenNotSet() {
        assertThat(testEndpoint.HEADERS.keySet(), not(hasItem(HttpHeaders.AUTHORIZATION)));
    }

}