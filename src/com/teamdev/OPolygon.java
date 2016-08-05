package com.teamdev;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class OPolygon {

    private FRange xRange;
    private FRange yRange;

    public List<Vertex> vertices = new ArrayList<>();

    public FRange getXRange() {
        if( xRange == null ) calcRanges();
        return xRange;
    }

    public FRange getYRange() {
        if( yRange == null ) calcRanges();
        return yRange;
    }

    private void calcRanges() {
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for (Vertex vertex : vertices) {
            float x = vertex.getFloatX();
            float y = vertex.getFloatY();

            if (minX > x) minX = x;
            if (minY > y) minY = y;
            if (maxX < x) maxX = x;
            if (maxY < y) maxY = y;
        }

        xRange = new FRange(minX, maxX);
        yRange = new FRange(minY, maxY);
    }

    public String toStringFormat() {
        String result = "";
        result += vertices.size() + "\n";
        for (Vertex v: vertices) {
            result += v + "\n";
        }
        return result;
    }
}
