package com.teamdev.fold;

import com.teamdev.Fraction;
import com.teamdev.Vertex;

/**
 * @author Vladislav Kovchug
 */
public class FoldLine {
    private double k;
    private double c;
    private Fraction fractionK;
    private Fraction fractionC;

    public FoldLine(Fraction k, Fraction c) {
        this.fractionK = k;
        this.fractionC = c;
        this.k = k.getFloatValue();
        this.c = c.getFloatValue();
    }

    public FoldLine(Vertex v1, Vertex v2) {
        final Vertex bottomPoint = v1.getFloatY() <= v2.getFloatY() ? v1 : v2;
        final Vertex topPoint = v1.getFloatY() > v2.getFloatY() ? v1 : v2;

        //k = -1 * (bottomPoint.y - topPoint.y) / ()
        //c = -1 * ( bottomPoint.x * topPoint.y - topPoint.x * bottomPoint.y )
        this.fractionK = new Fraction(-1, 1).mul(bottomPoint.y.sub(topPoint.y).div(topPoint.x.sub(bottomPoint.x)));
        this.fractionC = new Fraction(-1, 1).mul(bottomPoint.x.mul(topPoint.y).sub(topPoint.x.mul(bottomPoint.y)));
        this.k = this.fractionK.getFloatValue();
        this.c = this.fractionC.getFloatValue();
    }

    public Fraction getFractionK() {
        return fractionK;
    }

    public Fraction getFractionC() {
        return fractionC;
    }

    public double getK() {
        return k;
    }

    public double getC() {
        return c;
    }
}
