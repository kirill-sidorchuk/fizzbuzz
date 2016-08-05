package com.teamdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class SolutionParser {

    public static class ParsedSolution {
        public List<Vertex> inVertices;
        public List<Vertex> outVertices;
        public List<List<Integer>> facets;

        public ParsedSolution(List<Vertex> inVertices, List<Vertex> outVertices, List<List<Integer>> facets) {
            this.inVertices = inVertices;
            this.outVertices = outVertices;
            this.facets = facets;
        }
    }


    public void parse(File name) throws IOException {
        List<String> lines = Utils.readLines(name);

        // reading vertices
        List<Vertex> vertices = new ArrayList<>();
        int nVertices = Integer.parseInt(lines.get(0).trim());
        int pos = 1;
        for( int v=0; v<nVertices; ++v, ++pos) {
            vertices.add(new Vertex(lines.get(pos)));
        }

        // reading facets
        List<List<Integer>> facets = new ArrayList<>();
        int nFacets = Integer.parseInt(lines.get(pos).trim());
        pos++;
        for( int f=0; f<nFacets; ++f, ++pos) {
            String line = lines.get(pos).trim();
            int i = line.indexOf(' ');
            if( i == -1 ) throw new RuntimeException("failed to parse number of points, for facet #" + Integer.toString(f));

            List<Integer> facet = new ArrayList<>();
            facets.add(facet);
            int nPoints = Integer.parseInt(line.substring(0, i).trim());
            for( int p=0; p<nPoints; ++p) {
                int j = line.indexOf(' ', i+1);
                int index = Integer.parseInt(line.substring(i+1, j==-1 ? line.length() : j).trim());
                i = j;
                facet.add(index);
            }
        }

        // reading final vertices
        List<Vertex> finalVerts = new ArrayList<>();
        for (int v = 0; v < nVertices; ++v, ++pos) {
            finalVerts.add(new Vertex(lines.get(pos)));
        }

        ParsedSolution parsedSolution = new ParsedSolution(vertices, finalVerts, facets);
    }

    public static void main(String[] args) throws IOException {
        new SolutionParser().parse(new File(args[0]));
    }
}
