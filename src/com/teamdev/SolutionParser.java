package com.teamdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class SolutionParser {

    public Solution parse(File name) throws IOException {
        List<String> lines = Utils.readLines(name);

        // reading vertices
        List<Vertex> vertices = new ArrayList<>();
        int nVertices = Integer.parseInt(lines.get(0).trim());
        int pos = 1;
        for( int v=0; v<nVertices; ++v, ++pos) {
            Vertex vx = new Vertex(lines.get(pos));
            vx.makeValidFromSimpleFraction();
            vertices.add(vx);
        }
//        Converter.calculateVertexShifts(vertices);

        // reading facets
        List<Facet> facets = new ArrayList<>();
        int nFacets = Integer.parseInt(lines.get(pos).trim());
        pos++;
        for( int f=0; f<nFacets; ++f, ++pos) {
            String line = lines.get(pos).trim();
            int i = line.indexOf(' ');
            if( i == -1 ) throw new RuntimeException("failed to parse number of points, for facet #" + Integer.toString(f));

            Facet facet = new Facet();
            facets.add(facet);
            int nPoints = Integer.parseInt(line.substring(0, i).trim());
            for( int p=0; p<nPoints; ++p) {
                int j = line.indexOf(' ', i+1);
                int index = Integer.parseInt(line.substring(i+1, j==-1 ? line.length() : j).trim());
                i = j;
                facet.vertexIndices.add(index);
            }
        }

        // reading final vertices
        List<Vertex> finalVerts = new ArrayList<>();
        for (int v = 0; v < nVertices; ++v, ++pos) {
            Vertex vx = new Vertex(lines.get(pos));
            vx.makeValidFromSimpleFraction();
            finalVerts.add(vx);
        }

        return new Solution(vertices, facets, finalVerts);
    }

    public static void main(String[] args) throws IOException {
        Solution solution = new SolutionParser().parse(new File(args[0]));
        Solution translated = solution.translate(-3, 4, -1, 2);
        Solution rotated = translated.rotate(-Math.PI/4);
        String s = translated.toStringFormat();
        System.out.println(s);
    }
}
