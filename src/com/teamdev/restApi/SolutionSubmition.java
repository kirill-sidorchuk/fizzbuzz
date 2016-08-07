package com.teamdev.restApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * Created by cube on 06.08.2016.
 */
public class SolutionSubmition {
    private String postURl = ConstantParametrs.SOLUTION_SUBMIT_POST_URL;
    private String apiKey = ConstantParametrs.API_KEY;



    public void sendSubmitRecuest(int solutionId, String fileName) throws IOException, ParseException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(postURl);
        httpPost.addHeader("X-API-Key", apiKey);


        Map<String,Object> params = new LinkedHashMap<>();
        params.put("problem_id", solutionId);
        params.put("solution_spec", fileName);

        List<NameValuePair> param = new ArrayList<NameValuePair>(2);
        param.add(new BasicNameValuePair("problem_id", ""+solutionId));
        String spec = specSolution(fileName);
        param.add(new BasicNameValuePair("solution_spec", spec));

        httpPost.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity =  response.getEntity();

        printResultQuery(entity);





    }
    private void printResultQuery(HttpEntity entity) throws IOException {
        InputStream instream = entity.getContent();
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();

        try(InputStreamReader reader = new InputStreamReader(instream)) {

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);
        }
        Object o = sb.toString();
        System.out.println((String) o);
    }

    private BufferedReader openFileReader(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return reader;
    }
    public String specSolution(String fileName) {
        String result;
        StringBuilder solutionSpec = new StringBuilder();
        int rc;
        try (BufferedReader reader = openFileReader(fileName)) {
            while ((rc = reader.read()) != -1 ) {
               solutionSpec.append(reader.readLine());
                solutionSpec.append("/n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result = new String (solutionSpec);
    }
}

