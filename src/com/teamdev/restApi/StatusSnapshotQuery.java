package com.teamdev.restApi;

import org.apache.http.HttpEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ListIterator;


/**
 * Created by cube on 06.08.2016.
 */
public class StatusSnapshotQuery {
    private String getUrl = ConstantParametrs.STATUS_QUERY_GET_URL;
    private String apiKey = ConstantParametrs.API_KEY;


    private String sendStatusGet() throws IOException{

        GetHttpEntity httpEntity = new GetHttpEntity();

        HttpEntity entity = httpEntity.getGetRequest(getUrl);

        InputStream instream = entity.getContent();
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();

        try(InputStreamReader reader = new InputStreamReader(instream)) {

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);
        }
        Object o = sb.toString();
        return (String) o;
    }
    /*Get the latest hash.*/
    public String getSnapshotHash() throws ParseException {
        String obj = null;
        try {
            obj = sendStatusGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(obj);
       // System.out.println(jsonObject);
        JSONArray snapshots = (JSONArray) jsonObject.get("snapshots");
        ListIterator<JSONObject> iterator = snapshots.listIterator(snapshots.size());
        JSONObject snapshot =  iterator.previous();
        return (String)snapshot.get("snapshot_hash");
        }

}

