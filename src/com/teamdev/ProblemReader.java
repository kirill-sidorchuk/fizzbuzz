package com.teamdev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class ProblemReader {

    public static void read(File file) throws IOException {

        List<String> lines = readLines(file);

        int nPolys = Integer.parseInt(lines.get(0).trim());
        int pos = 1;
        for( int p=0; p<nPolys; ++p) {
            Polygon polygon = new Polygon();
            int nVerts = Integer.parseInt(lines.get(pos).trim());
            pos++;

            for( int v=0; v<nVerts; ++v, ++pos)
                polygon.vertices.add(new Vertex(lines.get(pos)));
        }

    }


    private static List<String> readLines(File file) throws IOException {
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
}
