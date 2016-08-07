package com.teamdev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Utils {

    static List<String> readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                lines.add(line);
            }
        }
        return lines;
    }

    public static void writeStringToFile(File file, String s) throws FileNotFoundException {
        try( PrintWriter out = new PrintWriter(file) ){
            out.println( s );
        }
    }

    static double getAngle(double sine, double cosine) {
        if( cosine >= 0 ) {
            return Math.asin(sine);
        }
        return sine >= 0 ? Math.acos(cosine) : -Math.acos(cosine);
    }

    public static void main(String[] args) {
        double a = -100.0 * Math.PI / 180.;

        double cosine = Math.cos(a);
        double sine = Math.sin(a);

        double r = 180 * getAngle(sine, cosine) / Math.PI;

        System.out.println(r);
    }

    static File getResultFile(File solutionFile) {
        return new File(solutionFile.getPath().replace("_solution.txt", "_result.txt"));
    }

    static boolean isPerfetlySolved(File solutionFile) {
        File resultFile = getResultFile(solutionFile);
        boolean isPerfetlySolved = false;
        try {
            List<String> lines = readLines(resultFile);
            isPerfetlySolved = lines.get(0).equals("1.0");
        } catch (IOException e) {
        }
        return isPerfetlySolved;
    }

    static File getSolutionFile(File problemFile) {
        return new File(problemFile.getPath().replace(".txt", "_solution.txt"));
    }
}
