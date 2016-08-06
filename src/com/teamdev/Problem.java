package com.teamdev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Problem {
    public List<OPolygon> polygons;
    public List<LineSegment> lineSegments;
    public VertexShift vertexShift;
    private FRange xRange;
    private FRange yRange;
    private Vertex center;

    public Problem(List<OPolygon> polygons, List<LineSegment> lineSegments) {
        this.polygons = polygons;
        this.lineSegments = lineSegments;
    }

    public FRange getXRange() {
        if( xRange == null ) calcRange();
        return xRange;
    }

    public FRange getYRange() {
        if( yRange == null ) calcRange();
        return yRange;
    }

    private void calcRange() {
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for (OPolygon polygon : polygons) {
            FRange xr = polygon.getXRange();
            FRange yr = polygon.getYRange();

            if( xr.min < minX ) minX = xr.min;
            if( xr.max > maxX ) maxX = xr.max;
            if( yr.min < minY ) minY = yr.min;
            if( yr.max > maxY ) maxY = yr.max;
        }

        xRange = new FRange(minX, maxX);
        yRange = new FRange(minY, maxY);
    }

    public Vertex getCenter() {
        if( center == null ) {
            List<Vertex> centers = new ArrayList<>();
            for (OPolygon polygon : polygons) {
                centers.add(polygon.getCenter());
            }
            center = Vertex.average(centers);
        }
        return center;
    }

    public String toStringFormat() {
        String result = "";

        result += polygons.size() + "\n";
        for (OPolygon polygon : polygons) {
            result += polygon.toStringFormat();
        }

        result += lineSegments.size() + "\n";
        for (LineSegment lineSegment : lineSegments) {
            result += lineSegment + "\n";
        }

        return result;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "polygons=" + polygons +
                ", lineSegments=" + lineSegments +
                '}';
    }

    public Origami getOrigami() {
        if (polygons.size() > 1) throw new RuntimeException("multi-polygonal problems not supported");

        OPolygon polygon = polygons.get(0);

        Fraction area = polygon.calcArea();
        System.out.println("polygon area = " + area + " = " + area.getFloatValue());

        Origami origami = new Origami();

        // adding external vertices (from silhouette)
        origami.vertices.addAll(polygon.vertices);
        origami.nContourVertices = polygon.vertices.size();

        for (Vertex v : origami.vertices) {
            v.external = true;
        }

        // adding external edges
//        for( int i=0; i<polygon.vertices.size(); ++i) {
//            int i1 = (i + 1) % polygon.vertices.size();
//            Edge e = new Edge(i, i1, true);
//
//            // adding links
//            origami.vertices.get(i).addEdge(e);
//            origami.vertices.get(i1).addEdge(e);
//            origami.edges.add(e);
//        }

        // adding hidden vertices and internal edges
        for (LineSegment seg : lineSegments) {
            seg.v1.external = false;
            seg.v2.external = false;

            int i1 = origami.vertices.indexOf(seg.v1);
            if (i1 == -1) {
                i1 = origami.vertices.size();
                origami.vertices.add(seg.v1);
            }
            int i2 = origami.vertices.indexOf(seg.v2);
            if (i2 == -1) {
                i2 = origami.vertices.size();
                origami.vertices.add(seg.v2);
            }

            Edge e = new Edge(i1, i2, false);
            if (!origami.edges.contains(e)) {
                origami.edges.add(e);
                origami.vertices.get(i1).addEdge(e);
                origami.vertices.get(i2).addEdge(e);
            }
        }

        // checking for intersections
        int totalCount = 0;
        int count = 1;
        while(count != 0) {
            count = 0;
            for (int i = 0; i < origami.edges.size() - 1 && count == 0; ++i) {
                Edge ei = origami.edges.get(i);
                for (int j = i + 1; j < origami.edges.size() && count == 0; ++j) {
                    Edge ej = origami.edges.get(j);
                    if (ei.hasCommonVertex(ej)) continue;
                    Vertex intersection = ei.getIntersection(ej, origami.vertices);
                    if (intersection != null) {
                        int intIndex = origami.vertices.indexOf(intersection);
                        if (intIndex == -1) {
                            // adding to list
                            intIndex = origami.vertices.size() - 1;
                            origami.vertices.add(intersection);

                            splitEdge(origami, ei, intersection, intIndex);
                            splitEdge(origami, ej, intersection, intIndex);
                        } else {
                            if (ei.containsIndex(intIndex)) {
                                splitEdge(origami, ej, intersection, intIndex);
                            } else {
                                splitEdge(origami, ei, intersection, intIndex);
                            }
                        }
                        count++;
                        totalCount++;
                    }
                }
            }
        }

        System.out.println("Count of intersections = " + count);

        return origami;
    }

    private static void splitEdge(Origami origami, Edge ej, Vertex intersection, int intIndex) {
        Vertex Bj0 = origami.vertices.get(ej.i0);
        Vertex Bj1 = origami.vertices.get(ej.i1);
        Bj0.edges.remove(ej);
        Bj1.edges.remove(ej);
        Edge ej0 = new Edge(ej.i0, intIndex, false);
        Edge ej1 = new Edge(intIndex, ej.i1, false);
        intersection.addEdge(ej0);
        intersection.addEdge(ej1);
        Bj0.edges.add(ej0);
        Bj1.edges.add(ej1);
        origami.edges.remove(ej);
        origami.edges.add(ej0);
        origami.edges.add(ej1);
    }

}
