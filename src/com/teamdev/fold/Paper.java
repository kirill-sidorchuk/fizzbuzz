package com.teamdev.fold;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @author Vladislav Kovchug
 */
public class Paper {
    private List<Point2D.Float> vertices;

    public Paper(List<Point2D.Float> vertices) {
        this.vertices = vertices;
    }

    public List<Point2D.Float> getVertices() {
        return vertices;
    }

    public void setVertices(List<Point2D.Float> vertices) {
        this.vertices = vertices;
    }
}
