package com.teamdev.restApi;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by cube on 06.08.2016.
 */
public class MainTest {
    public static void main(String[] args) throws  IOException, ParseException {
        HelloTest test = new HelloTest();
        StatusSnapshotQuery status = new StatusSnapshotQuery();
        SolutionSubmition submition = new SolutionSubmition();
        BlobLoockUp blobRequest = new BlobLoockUp ();
        GetProblemSpec getProb = new GetProblemSpec();
        //write problems spec to the file
        getProb.problemsWriter("C:\\workFolder\\projects\\restHttp\\src\\main\\java\\problemHash.txt");

//    try {
        //test connect restApi
////      System.out.print(test.sendHelloGet());
        //Get timeStampHash
////     String hash = status.getSnapshotHash();
////     System.out.println(hash);
//       Thread.sleep(200);
        //Create File with problems hash
//       blobRequest.getListProblemHashs("hash", "C:\\workFolder\\projects\\restHttp\\src\\main\\java\\problemIdHash.txt" );

//         } catch (ParseException e) {
//             e.printStackTrace();
//
//
//       }
   }
}
