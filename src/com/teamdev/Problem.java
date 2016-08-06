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
        if( polygons.size() > 1 ) throw new RuntimeException("multi-polygonal problems not supported");

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
        for( int i=0; i<polygon.vertices.size(); ++i) {
            int i1 = (i + 1) % polygon.vertices.size();
            Edge e = new Edge(i, i1, true);

            // adding links
            origami.vertices.get(i).addEdge(e);
            origami.vertices.get(i1).addEdge(e);
            origami.edges.add(e);
        }

        // adding hidden vertices and internal edges
        for (LineSegment seg : lineSegments) {
            seg.v1.external = false;
            seg.v2.external = false;

            int i1 = origami.vertices.indexOf(seg.v1);
            if( i1 == -1 ) {
                i1 = origami.vertices.size();
                origami.vertices.add(seg.v1);
            }
            int i2 = origami.vertices.indexOf(seg.v2);
            if( i2 != -1 ) {
                i2 = origami.vertices.size();
                origami.vertices.add(seg.v2);
            }

            Edge e = new Edge(i1, i2, false);
            if( !origami.edges.contains(e) ) {
                origami.edges.add(e);
                origami.vertices.get(i1).addEdge(e);
                origami.vertices.get(i2).addEdge(e);
            }
        }

        // searching for facets
        for( int i=0; i<origami.vertices.size(); ++i) {
            Vertex startVert = origami.vertices.get(i);
            int j = startVert.edges.get(0).i0;
            if( j == i) j = startVert.edges.get(0).i1;
            Set<Integer> indexes = new HashSet<>();
            indexes.add(i);
            indexes.add(j);

            while(!indexes.contains(j)) {
                // selecting where to go

//                startVert.addEdkge();

            }
        }

        return origami;
    }

}
