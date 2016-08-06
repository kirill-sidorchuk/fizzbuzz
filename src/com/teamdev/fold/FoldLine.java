package com.teamdev.fold;

import com.teamdev.Vertex;

/**
 * @author Vladislav Kovchug
 */
public class FoldLine {
    private double k;
    private double c;

    public FoldLine(double k, double c) {
        this.k = k;
        this.c = c;
    }

    public FoldLine(Vertex v1, Vertex v2) {
        final Vertex bottomPoint = v1.getFloatY() <= v2.getFloatY() ? v1 : v2;
        final Vertex topPoint = v1.getFloatY() > v2.getFloatY() ? v1 : v2;

        this.k = (bottomPoint.getFloatY() - topPoint.getFloatY())/(topPoint.getFloatX() - bottomPoint.getFloatX())*-1;
        this.c = (bottomPoint.getFloatX()*topPoint.getFloatY() - topPoint.getFloatX()*bottomPoint.getFloatY())*-1;
    }

    public double getK() {
        return k;
    }

    public double getC() {
        return c;
    }
}
