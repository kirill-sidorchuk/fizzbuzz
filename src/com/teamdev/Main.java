package com.teamdev;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final String TEST_FILE_PATH = "problems/initial/1.txt";

    public static void main(String[] args) throws IOException {
	    // problem reading test
        File testFile = new File(TEST_FILE_PATH);
        Problem problem = ProblemReader.read(testFile);

        System.out.println("Polygons:");
        for (Polygon p: problem.polygons) {
            System.out.println(p.toString());
        }

        System.out.println("Line segments:");
        for (LineSegment l: problem.lineSegments) {
            System.out.println(l.toString());
        }
    }
}
