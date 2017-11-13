package com.github.ganop;

import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void HttpRequestGetTest() throws Exception {
        URL google = new URL("https", "www.google.co.uk", "/");

        assertTrue(true);
    }

}