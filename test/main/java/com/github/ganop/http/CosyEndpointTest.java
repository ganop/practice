package com.github.ganop.http;

import org.apache.http.HttpHeaders;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CosyEndpointTest {
    @Test
    public void setTokenMethodSetsValidAuthorizationString() {
        String authorization = "ASHFNASknISNXO09092uhrqha9donb3";
        CosyEndpoint testEndpoint = CosyEndpoint.TEMP_DATA_ENDPOINT;

        testEndpoint.setSecurityToken(authorization);

        assert testEndpoint.HEADERS != null;
        assertThat(testEndpoint.HEADERS.keySet(), hasItem(HttpHeaders.AUTHORIZATION));
        assertThat(testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION), is("Bearer " + authorization));
    }

    @Test
    public void setTokenMethodOverwritesPreviousToken() {
        String token1 = "ASHFNASknISNXO09092uhrqha9donb3";
        String token2 = "ASJOlUAmHEFO79ATERGb76aert967";
        CosyEndpoint testEndpoint = CosyEndpoint.TEMP_DATA_ENDPOINT;

        testEndpoint.setSecurityToken(token1);
        String authorization1 = testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION);
        testEndpoint.setSecurityToken(token2);
        String authorization2 = testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION);

        assertThat(authorization2, not(authorization1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void setTokenMethodDoesNotAcceptSpacesInToken() {
        String token1 = "ASHFNASknISNXO09092 uhrqha9donb3";
        CosyEndpoint testEndpoint = CosyEndpoint.TEMP_DATA_ENDPOINT;

        testEndpoint.setSecurityToken(token1);
        String authorization1 = testEndpoint.HEADERS.get(HttpHeaders.AUTHORIZATION);
    }

}