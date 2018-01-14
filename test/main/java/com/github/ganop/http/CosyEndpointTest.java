package com.github.ganop.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class CosyEndpointTest {
    @Test
    public void checkJsonToStringContainsNoNewLineChars() {
        JsonObject json = new JsonObject();
        json.add("name", new JsonPrimitive("test_name"));
        json.add("password", new JsonPrimitive("test_password"));

        String payload = json.toString();

        System.out.println(payload);

        assertThat(payload, not(containsString(System.lineSeparator())));
    }
}