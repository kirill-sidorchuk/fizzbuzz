package com.teamdev.restApi;//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.*;
//import java.net.URLEncoder;
//
//import static java.lang.System.out;
//
///**
// * Created by cube on 06.08.2016.
// */
//public class SolutionSubmition {
//    private String postURl = ConstantParametrs.SOLUTION_SUBMIT_POST_URL;
//    private String apiKey = ConstantParametrs.API_KEY;
//
//
//
//    private JSONObject sendSubmitRecuest(int solutionId, String fileName) throws IOException, ParseException {
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(postURl);
//        httpPost.addHeader("X-API-Key", apiKey);
//        httpPost.addHeader("problem_id", "" + solutionId);
//        httpPost.addHeader("solution_spec", fileName);
//        HttpResponse response = httpClient.execute(httpPost);
//
//        httpPost.setEntity();
//
//        private void sendFile(OutputStream out, String name, InputStream in, String fileName) throws IOException {
//            String o = "Content-Disposition: form-data; name=\"" + URLEncoder.encode(name,"UTF-8")
//                    + "\"; filename=\"" + URLEncoder.encode(filename,"UTF-8") + "\"\r\n\r\n");
//            out.write(o.getBytes(StandartCharsets.UTF_8));
//            byte[] buffer = new byte[2048];
//            for (int n = 0; n >= 0; n = in.read(buffer))
//                out.write(buffer, 0, n);
//            out.write("\r\n".getBytes(StandartCharsets.UTF_8));
//        }
//
//        private void sendField(OutputStream out, String name, String field) throws UnsupportedEncodingException {
//            String o = "Content-Disposition: form-data; name=\""
//                    + URLEncoder.encode(name,"UTF-8") + "\"\r\n\r\n");
//            out.write(o.getBytes(StandartCharsets.UTF_8));
//            out.write(URLEncoder.encode(field,"UTF-8").getBytes(StandartCharsets.UTF_8));
//            out.write("\r\n".getBytes(StandartCharsets.UTF_8));
//        }
//    String boundary = UUID.randomUUID().toString();
//    byte[] boundaryBytes = boundaryBytes =
//            ("--" + boundary + "\r\n").getBytes(StandartCharsets.UTF_8);
//    byte[] finishBoundaryBytes =
//            ("--" + boundary + "--").getBytes(StandartCharsets.UTF_8);
//    http.setRequestProperty("Content-Type",
//            "multipart/form-data; charset=UTF-8; boundary=" + boundary);
//
//// Enable streaming mode with default settings
//    http.setChunkedStreamingMode(0);
//
//// Send our fields:
//    try(OutputStream out = http.getOutputStream()) {
//        // Send our header (thx Algoman)
//        out.write(boundaryBytes);
//
//        // Send our first field
//        sendField(out, "username", "root");
//
//        // Send a seperator
//        out.write(boundaryBytes);
//
//        // Send our second field
//        sendField(out, "password", "toor");
//
//        // Send another seperator
//        out.write(boundaryBytes);
//
//        // Send our file
//        try(InputStream file = new FileInputStream("test.txt")) {
//            sendFile(out, "identification", file, "text.txt");
//        }
//
//        // Finish the request
//        out.write(finishBoundaryBytes);
//    }
//
//
//}
//
//}
//
