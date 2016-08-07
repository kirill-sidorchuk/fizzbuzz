package com.teamdev.restApi;

import com.teamdev.Utils;
import org.apache.http.HttpEntity;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

/**
 * Created by cube on 07.08.2016.
 */
public class GetProblemSpec {

    private String getUrl = ConstantParametrs.BLOB_LOOKUP_GET_URL;
    private int id = 0;

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

    public void getProblemSpec(String hash, File dstDir, String id) throws ParseException, IOException {
        String obj = sendBlobProblemsGet(hash);
        writeToFile(obj.toString(), dstDir, id);
    }

    private void writeToFile(String spec, File dstDir, String id) throws IOException {
        File f1 = new File(dstDir, id + ".txt");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f1), "utf-8"))) {
            writer.write(spec);
            writer.flush();
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

    public void downloadAll(String fileName, File dstDir) throws IOException {

        List<String> lines = null;
        try {
            lines = Utils.readLines(new File(fileName));
        } catch (IOException e) {
            System.out.println("Failed to read problems hash file");
            e.printStackTrace();
            throw e;
        }

        for (String line : lines) {
            try {
                int i = line.indexOf(" ");
                if (i == -1) throw new RuntimeException("failed to parse hash line: " + line);

                String id = line.substring(0, i).trim();
                String problemHash = line.substring(i + 1).trim();

                File problemFile = new File(dstDir, id + ".txt");
                if( !problemFile.exists()) {

                    System.out.println(id);
                    getProblemSpec(problemHash, dstDir, id);
                    Thread.sleep(200);
                }

            }catch (Exception e) {
                System.out.println("Failed to download problem: " + line);
                e.printStackTrace();
            }
        }
    }
}


