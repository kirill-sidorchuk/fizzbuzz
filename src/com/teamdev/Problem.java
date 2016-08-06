package com.teamdev;

import java.util.ArrayList;
import java.util.List;

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

        Origami origami = new Origami();

        origami.vertices.addAll(polygon.vertices);
        origami.nContourVertices = polygon.vertices.size();

        // adding hidden vertices
        for (LineSegment seg : lineSegments) {
            int i1 = origami.vertices.indexOf(seg.v1);
            if( i1 == -1 ) {
                origami.vertices.add(seg.v1);
            }
            int i2 = origami.vertices.indexOf(seg.v2);
            if( i2 != -1 ) {
                origami.vertices.add(seg.v2);
            }

        }


        return origami;
    }

}
