package com.teamdev.restApi;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class ProblemsDownloader {

    public static void main(String[] args) throws ParseException, InterruptedException, IOException {
        File dstDir = new File(args[0]);

        // all system time stamp get
        System.out.println("getting system snapshot");
        StatusSnapshotQuery status = new StatusSnapshotQuery();
        String hash = status.getSnapshotHash();

        Thread.sleep(1000);

        //Create File with problems hash
        System.out.println("downloading problem ids");
        BlobLoockUp blobRequest = new BlobLoockUp();
        File problemIdHashFile = new File(dstDir, "problemIdHash.txt");
        try {
            blobRequest.getListProblemHashs(hash, problemIdHashFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Thread.sleep(1000);

        System.out.println("downloading all problems");
        GetProblemSpec getProb = new GetProblemSpec();
        getProb.problemsWriter(problemIdHashFile.getPath(), dstDir);


    }
}
