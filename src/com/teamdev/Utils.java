package com.teamdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
}
