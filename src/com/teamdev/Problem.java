package com.teamdev;

import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Problem {
    public List<Polygon> polygons;
    public List<LineSegment> lineSegments;

    public Problem(List<Polygon> polygons, List<LineSegment> lineSegments) {
        this.polygons = polygons;
        this.lineSegments = lineSegments;
    }
}
