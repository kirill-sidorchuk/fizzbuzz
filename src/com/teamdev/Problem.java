package com.teamdev;

import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Problem {
    public List<OPolygon> polygons;
    public List<LineSegment> lineSegments;

    public Problem(List<OPolygon> polygons, List<LineSegment> lineSegments) {
        this.polygons = polygons;
        this.lineSegments = lineSegments;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "polygons=" + polygons +
                ", lineSegments=" + lineSegments +
                '}';
    }
}
