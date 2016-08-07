package com.teamdev.restApi;

import org.apache.http.HttpEntity;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Created by cube on 07.08.2016.
 */
public class GetProblemSpec {

        private String getUrl = ConstantParametrs.BLOB_LOOKUP_GET_URL;
        private int id=0;

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

        public void getProblemSpec(String hash) throws ParseException {
            String obj = null;

            try {
                obj = sendBlobProblemsGet(hash);
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeToFile(obj.toString());

        }

        private void writeToFile(String  spec){
            id++;
            String fname= "C:\\workFolder\\projects\\restHttp\\src\\main\\java\\ProblemSpec\\problemSpec_" + id + ".txt";
            File f1 = new File(fname);
            try {
                f1.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

//            JSONObject object;
//            StringBuffer buffer = new StringBuffer();
//            String str;
//            for(Object problem: problems) {
//                object = (JSONObject) problem;
//                str = object.get("problem_spec_hash").toString();
//                buffer.append(str);
//                buffer.append("\n");
//            }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(f1), "utf-8"))) {
                writer.write(spec);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void problemsWriter(String fileName) {

        int rc;
        try (BufferedReader reader = openFileReader(fileName)) {
            while ((rc = reader.read()) != -1 ) {
                String problemHash = reader.readLine();
                getProblemSpec(problemHash);

                Thread.sleep(200);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    }


