package com.teamdev;

import sun.plugin.dom.exception.InvalidStateException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Vertex {

    public List<Edge> edges;

    boolean shifted = false;

    public Fraction x;
    public Fraction y;

    // x in Big Decimal
    public BigDecimal nXBig; // n
    public BigDecimal dXBig; // d

    // y in Big Decimal
    public BigDecimal nYBig; // n
    public BigDecimal dYBig; // d

    public Vertex() {
        x = new Fraction(0, 1);
        y = new Fraction(0, 1);

        nXBig = new BigDecimal(0);
        dXBig = new BigDecimal(1);
        nYBig = new BigDecimal(0);
        dYBig = new BigDecimal(1);
    }

    public Vertex(Fraction x, Fraction y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(BigDecimal nX, BigDecimal dX, BigDecimal nY, BigDecimal dY) {
        this.nXBig = nX;
        this.dXBig = dX;
        this.nYBig = nY;
        this.dYBig = dY;
    }


    public Vertex(long nX, long dX, long nY, long dY) {
        x = new Fraction(nX, dX);
        y = new Fraction(nY, dY);
        shifted = true;
    }

    public Vertex(String line) {
        int i = line.indexOf(',');
        if( i == -1 ) throw new RuntimeException("Failed to parse line: " + line);

        String xLine = line.substring(0, i).trim();
        String yLine = line.substring(i+1).trim();

        i = xLine.indexOf('/');
        if( i == -1 ) {
            nXBig = new BigDecimal(xLine);
            dXBig = new BigDecimal(1);
        }
        else {
            nXBig = new BigDecimal(xLine.substring(0, i).trim());
            dXBig = new BigDecimal(xLine.substring(i+1).trim());
        }

        i = yLine.indexOf('/');
        if( i == -1 ) {
            nYBig = new BigDecimal(yLine);
            dYBig = new BigDecimal(1);
        }
        else {
            nYBig = new BigDecimal(yLine.substring(0, i).trim());
            dYBig = new BigDecimal(yLine.substring(i+1).trim());
        }
    }

    public void makeValidFromSimpleFraction() {
        x = new Fraction(nXBig.longValue(), dXBig.longValue());
        y = new Fraction(nYBig.longValue(), dYBig.longValue());
        shifted = true;
    }

    public void addEdge(Edge e) {
        if( edges == null ) edges = new ArrayList<>();
        edges.add(e);
    }

    public Vertex translate(Vertex v) {
        Fraction _x = x.add(v.x);
        Fraction _y = y.add(v.y);
        return new Vertex(_x, _y).normalize();
        // todo add big decimals
    }

    public Vertex rotate(double[] R) {
        float fx = getFloatX();
        float fy = getFloatY();

        double rx = R[0] * fx + R[1] * fy;
        double ry = R[2] * fx + R[3] * fy;

        final long ACC = 80000000;
        return new Vertex( Math.round(rx*ACC), ACC, Math.round(ry*ACC), ACC).normalize();
    }

    public static Vertex average(List<Vertex> list) {
        double x = 0;
        double y = 0;
        for (Vertex v : list) {
            x += v.getFloatX();
            y += v.getFloatY();
        }

        final long ACC = 10000;
        long den = ACC * list.size();

        return new Vertex(Math.round(ACC*x), den, Math.round(y*ACC), den).normalize();
    }

    public Vertex normalize() {
        x.normalize();
        y.normalize();
        return this;
    }

    private void checkShifted(){
        if (!shifted) {
            throw new InvalidStateException("Vertex is not shifted");
        }
    }

    public float getFloatX() {
        checkShifted();
        return x.getFloatValue();
    }

    public float getFloatY() {
        checkShifted();
        return y.getFloatValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return x.n == vertex.x.n && x.d == vertex.x.d && y.n == vertex.y.n && y.d == vertex.y.d;

    }

    @Override
    public int hashCode() {
        int result = (int) (x.n ^ (x.n >>> 32));
        result = 31 * result + (int) (x.d ^ (x.d >>> 32));
        result = 31 * result + (int) (y.n ^ (y.n >>> 32));
        result = 31 * result + (int) (y.d ^ (y.d >>> 32));
        return result;
    }

    @Override
    public String toString() {
        String vdx_str = "";
        if (x.d != 1) {
            vdx_str = "/" + x.d;
        }
        String vdy_str = "";
        if (y.d != 1) {
            vdy_str = "/" + y.d;
        }
        return x.n + vdx_str + "," + y.n + vdy_str;
    }
}
