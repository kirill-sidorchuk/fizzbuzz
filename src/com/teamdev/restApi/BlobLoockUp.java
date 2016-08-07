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

        try(InputStreamReader reader = new InputStreamReader(instream)) {

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);
        }
        Object o = sb.toString();
        return (String) o;

    }
    /*Get the latest hash.*/
    public void getListProblemHashs(String hash) throws ParseException {
        String obj = null;
        try {
            obj = sendBlobProblemsGet(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(obj);
        System.out.println(jsonObject);
        JSONArray problems = (JSONArray) jsonObject.get("problems");

        JSONObject object;
        StringBuffer buffer = new StringBuffer();
        String str;
//        for(Object problem: problems) {
//            object = (JSONObject) problem;
//            str = object.get("problem_id").toString();
//            System.out.println(str);
     //   }
           writeToFile(problems);


//        Map<String, String> listOfProblems = new HashMap<>();
//        ListIterator<JSONObject> iterator = problems.listIterator();
//
//        while(iterator.hasNext()){
//            JSONObject problem = (JSONObject) jsonParser.parse(String.valueOf(iterator.next()));
//            listOfProblems.put((String)jsonObject.get("problem_id"), (String)jsonObject.get("problem_spec_hash"));
//        }
//       Iterator <String> it = listOfProblems.keySet().iterator();
//       while(it.hasNext()){
//           System.out.println(it.next()+"  " + listOfProblems.get(it.next()));
//       }
    }
    private void writeToFile(JSONArray   problems){
        String fname= "C:\\workFolder\\projects\\restHttp\\src\\main\\java\\problemIdHash.txt";
        File f1 = new File(fname);
        try {
            f1.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JSONObject object;
        StringBuffer buffer = new StringBuffer();
        String str;
        for(Object problem: problems) {
            object = (JSONObject) problem;
            str = object.get("problem_id").toString() +" "+ object.get("problem_spec_hash").toString();
            buffer.append(str);
            buffer.append("\n");
        }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("C:\\workFolder\\projects\\restHttp\\src\\main\\java\\problemIdHash.txt"), "utf-8"))) {
                writer.write(""+buffer);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }






//    private BufferedWriter openFileWriter(String filename) {
//        BufferedWriter writer = null;
//        try {
//            reader = new BufferedReader(new FileReader(filename));
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found.");
//        }
//        return reader;
//    }
//    public String problemsWriter(String fileName) {
//        String result;
//        StringBuilder problemsList = new StringBuilder();
//        int rc;
//        try (BufferedReader reader = openFileReader(fileName)) {
//            while ((rc = reader.read()) != -1 ) {
//                problemsList.append(reader.readLine());
//                problemsList.append("/n");
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return result = new String (problemsList);
//    }
}

