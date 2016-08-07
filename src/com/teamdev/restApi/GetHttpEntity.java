package com.teamdev.restApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by Mikhail on 07.08.16.
 */
public class GetHttpEntity {
    private String apiKey = ConstantParametrs.API_KEY;

    public HttpEntity getGetRequest(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("X-API-Key", apiKey);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        return entity;
    }
    public HttpEntity getGetRequest(String url, String hash) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url+"/"+ hash);
        httpGet.addHeader("X-API-Key", apiKey);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        return entity;
    }

    public HttpEntity getPostRequest(String url, String apiKey) {
        HttpEntity entity = null;
        return entity;

    }


}
