package com.teamdev;

/**
 * Created by dmitriy.kuzmin on 8/5/2016.
 */
public class LineSegment {
    public Vertex v1;
    public Vertex v2;

    public LineSegment(String line) {
        int i = line.indexOf(' ');

        String v1Line = line.substring(0, i).trim();
        v1 = new Vertex(v1Line);

        String v2Line = line.substring(i+1).trim();
        v2 = new Vertex(v2Line);
    }

    @Override
    public String toString() {
        return "LineSegment{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }
}
