package com.github.ganop;

import com.github.ganop.data.TemperatureEntry;
import com.github.ganop.http.CosyEndpoint;
import com.github.ganop.http.RequestBuilder;
import com.github.ganop.http.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

import static com.github.ganop.http.CosyEndpoint.LOGIN_ENDPOINT;
import static com.github.ganop.http.CosyEndpoint.TEMP_DATA_ENDPOINT;

public class CosyEndpointExtractor implements DataExtractor {
    private String token;
    private RequestBuilder rBuilder;
    private ResponseHandler rHandler;
    private CloseableHttpClient client;

    public CosyEndpointExtractor() {
        this.rBuilder = new RequestBuilder();
        this.rHandler = new ResponseHandler();
        this.client = HttpClients.createDefault();
    }

    private HttpUriRequest prepareRequest(CosyEndpoint cosyEndpoint) {
        rBuilder.newRequest();
        rBuilder.withURL(cosyEndpoint.getUrl()).withMethod(cosyEndpoint.getMethod());
        if (cosyEndpoint == TEMP_DATA_ENDPOINT){
            cosyEndpoint.setSecurityToken(getToken());
        }
        rBuilder.withHeaders(cosyEndpoint.getHeaders());
        rBuilder.withEntity(cosyEndpoint.getEntity());

        return rBuilder.build();
    }

    @Override
    public boolean connect() {
        HttpUriRequest loginRequest = prepareRequest(LOGIN_ENDPOINT);
        try (CloseableHttpResponse response = client.execute(loginRequest)) {
            String token = (String) rHandler.handle(response,"token");
            setToken(token);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TemperatureEntry getData() {
        HttpUriRequest dataRequest = prepareRequest(TEMP_DATA_ENDPOINT);
        try (CloseableHttpResponse response = client.execute(dataRequest)){
            TemperatureEntry temp = (TemperatureEntry) rHandler.handle(response, "data");
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean disconnect() {
        //TODO
        return false;
    }

    private void setToken(String token) {
        this.token = token;
    }

    private String getToken (){
        return this.token;
    }
}
