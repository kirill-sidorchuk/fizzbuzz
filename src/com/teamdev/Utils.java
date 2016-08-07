package com.teamdev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Utils {

    public static List<String> readLines(File file) throws IOException {
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

    public static double getAngle(double sine, double cosine) {
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

    public static File getResultFile(File solutionFile) {
        return new File(solutionFile.getPath().replace("_solution.txt", "_result.txt"));
    }

    public static String getResultString(File solutionFile) {
        File resultFile = getResultFile(solutionFile);
        String resultString = "";
        try {
            List<String> lines = readLines(resultFile);
            resultString = lines.get(0);
        } catch (IOException e) {
        }
        return resultString;
    }

    public static boolean isPerfectlySolved(File solutionFile) {
        String resultString = getResultString(solutionFile);
        return resultString.equals("1.0");
    }

    public static boolean isSolved(File solutionFile) {
        File resultFile = getResultFile(solutionFile);
        boolean isSolved = false;
        try {
            List<String> lines = readLines(resultFile);
            String s = lines.get(0);
            boolean parsable = true;
            try {
                Double.parseDouble(s);
            } catch (NumberFormatException e) {
                parsable = false;
            }
            isSolved = !s.equals("0.0") && parsable;
        } catch (IOException e) {
        }
        return isSolved;
    }

    public static File getSolutionFile(File problemFile) {
        return new File(problemFile.getPath().replace(".txt", "_solution.txt"));
    }
}
