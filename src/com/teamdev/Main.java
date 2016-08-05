package com.teamdev;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final String TEST_FILE_PATH = "problems/initial";

    public static void main(String[] args) throws IOException {
        if( args.length < 1 ) {
            System.out.println("problem id is expected in command line");
            return;
        }

        String problemID = args[0];
        Problem problem = ProblemReader.read(new File(TEST_FILE_PATH, problemID + ".txt"));


        ProblemVisualizer.visualizeProblem(problem, problemID + ".png");
    }
}
