package com.teamdev.restApi;

import org.apache.http.HttpEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Created by cube on 06.08.2016.
 */
public class BlobLoockUp {
    private String getUrl = ConstantParametrs.BLOB_LOOKUP_GET_URL;


    private String sendBlobProblemsGet(String snapshotHash) throws IOException {
        GetHttpEntity httpEntity = new GetHttpEntity();

        HttpEntity entity = httpEntity.getGetRequest(getUrl, snapshotHash);

        InputStream instream = entity.getContent();
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();

        try (InputStreamReader reader = new InputStreamReader(instream)) {

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);
        }
        Object o = sb.toString();
        return (String) o;

    }

    public void getListProblemHashs(String hash, String fileName) throws ParseException, IOException {
        String obj = null;
        try {
            obj = sendBlobProblemsGet(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   System.out.println(obj.toString());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(obj);
        JSONArray problems = (JSONArray) jsonObject.get("problems");
        writeToFile(problems, fileName);

//        JSONObject object;
//        StringBuffer buffer = new StringBuffer();
//        String str;
//        for(Object problem: problems) {
//            object = (JSONObject) problem;
//            str = object.get("problem_id").toString();
//            System.out.println(str);
//        }


    }

    private void writeToFile(JSONArray problems, String fileName) throws IOException {

        JSONObject object;
        StringBuilder sb = new StringBuilder();
        for (Object problem : problems) {
            object = (JSONObject) problem;
            sb.append(object.get("problem_id").toString());
            sb.append(" ");
            sb.append(object.get("problem_spec_hash").toString());
            sb.append("\n");
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
            writer.write(sb.toString());
            writer.flush();
        }
    }


}

