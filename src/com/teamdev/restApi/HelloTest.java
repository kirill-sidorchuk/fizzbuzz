package com.teamdev.restApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by cube on 06.08.2016.
 */
public class HelloTest {
    private static String getUrl = ConstantParametrs.HELLO_TEST_GET_URL;
    private static String apiKey = ConstantParametrs.API_KEY;

    public static JSONObject sendHelloGet() throws IOException, ParseException {

       CloseableHttpClient httpClient = HttpClients.createDefault();
       HttpGet httpGet = new HttpGet(getUrl);
       httpGet.addHeader("X-API-Key", apiKey);
       HttpResponse response = httpClient.execute(httpGet);
       HttpEntity entity = response.getEntity();
       InputStream instream =  entity.getContent();
       InputStreamReader reader = new InputStreamReader(instream);
       char[] buffer = new char[256];
       int rc;
       StringBuilder sb = new StringBuilder();
       while ((rc = reader.read(buffer)) != -1)
           sb.append(buffer, 0, rc);
       reader.close();
       JSONParser jsonParser = new JSONParser();
       Object o = jsonParser.parse(sb.toString());
       return (JSONObject) o;
     }

    }
