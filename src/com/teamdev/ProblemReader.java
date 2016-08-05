package com.teamdev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class ProblemReader {

    public static Problem read(File file) throws IOException {
        List<String> lines = readLines(file);

        List<Polygon> polygons = new ArrayList<>();

        int nPolys = Integer.parseInt(lines.get(0).trim());
        int pos = 1;
        for( int p=0; p<nPolys; ++p) {
            Polygon polygon = new Polygon();
            int nVerts = Integer.parseInt(lines.get(pos).trim());
            pos++;

            for( int v=0; v<nVerts; ++v, ++pos) {
                polygon.vertices.add(new Vertex(lines.get(pos)));
            }

            polygons.add(polygon);
        }

        List<LineSegment> lineSegments = new ArrayList<>();

        int nLines = Integer.parseInt(lines.get(pos).trim());
        pos++;
        for( int p=0; p<nLines; ++p) {
            LineSegment lineSegment = new LineSegment(lines.get(pos).trim());
            pos++;
            lineSegments.add(lineSegment);
        }

        return new Problem(polygons, lineSegments);
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
