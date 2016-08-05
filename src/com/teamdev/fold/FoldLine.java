package com.teamdev.fold;

import java.awt.geom.Point2D;

/**
 * @author Vladislav Kovchug
 */
public class FoldLine {
    private Point2D.Float v1;
    private Point2D.Float v2;

    public FoldLine(Point2D.Float v1, Point2D.Float v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Point2D.Float getV1() {
        return v1;
    }

    public Point2D.Float getV2() {
        return v2;
    }
}
