package com.teamdev;

import sun.plugin.dom.exception.InvalidStateException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Vertex {

    public List<Edge> edges;

    public Fraction x;
    public Fraction y;

    public boolean external = true;

    public Vertex() {
        x = new Fraction(0, 1);
        y = new Fraction(0, 1);
    }

    public Vertex(Fraction x, Fraction y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(long nX, long dX, long nY, long dY) {
        x = new Fraction(nX, dX);
        y = new Fraction(nY, dY);
    }

    public Vertex(String line) {
        int i = line.indexOf(',');
        if( i == -1 ) throw new RuntimeException("Failed to parse line: " + line);

        String xLine = line.substring(0, i).trim();
        String yLine = line.substring(i+1).trim();

        BigInteger nX;
        BigInteger dX;
        BigInteger nY;
        BigInteger dY;

        i = xLine.indexOf('/');
        if( i == -1 ) {
            nX = new BigInteger(xLine);
            dX = BigInteger.ONE;
        }
        else {
            nX = new BigInteger(xLine.substring(0, i).trim());
            dX = new BigInteger(xLine.substring(i+1).trim());
        }

        i = yLine.indexOf('/');
        if( i == -1 ) {
            nY = new BigInteger(yLine);
            dY = BigInteger.ONE;
        }
        else {
            nY = new BigInteger(yLine.substring(0, i).trim());
            dY = new BigInteger(yLine.substring(i+1).trim());
        }
        
        x = new Fraction(nX, dX).normalize();
        y = new Fraction(nY, dY).normalize();
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
        final double ACC = 1024;
        long rx = Math.round(ACC * (R[0] * x.n.multiply(y.d).doubleValue() + R[1] * y.n.multiply(x.d).doubleValue()));
        long ry = Math.round(ACC * (R[2] * x.n.multiply(y.d).doubleValue() + R[3] * y.n.multiply(x.d).doubleValue()));
        long den = Math.round(x.d.multiply(y.d).doubleValue() * ACC);
        return new Vertex( rx, den, ry, den).normalize();
    }

    public Vertex sub(Vertex vertex) {
        return new Vertex(x.sub(vertex.x), y.sub(vertex.y));
    }

    public Vertex add(Vertex v) {
        return new Vertex(x.add(v.x), y.add(v.y));
    }

    public Vertex mul(Vertex v) {
        return new Vertex(x.mul(v.x), y.mul(v.y));
    }

    public Vertex mul(Fraction f) {
        return new Vertex(x.mul(f), y.mul(f));
    }

    public Fraction normSquared() {
        return x.mul(x).add(y.mul(y));
    }

    public Fraction vect_prod_z(Vertex v) {
        return x.mul(v.y).sub(y.mul(v.x));
    }

    public Fraction scalarMul(Vertex v) {
        return x.mul(v.x).add(y.mul(v.y));
    }

    public double getSinus(Vertex v) {
        double vect_prod = vect_prod_z(v).getDoubleValue();
        double norm = Math.sqrt(normSquared().mul(v.normSquared()).getDoubleValue());
        return vect_prod / norm;
    }

    public static Vertex average(List<Vertex> list) {
        Vertex sum = new Vertex();
        for (Vertex v : list) {
            sum = sum.add(v);
        }

        if( list.size() != 0 ) sum = sum.div(list.size());
        return sum;
    }

    private Vertex div(long c) {
        return new Vertex(x.div(c), y.div(c)).normalize();
    }

    public Vertex normalize() {
        x.normalize();
        y.normalize();
        return this;
    }

    public float getFloatX() {
        return x.getFloatValue();
    }

    public float getFloatY() {
        return y.getFloatValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return x.equals(vertex.x) && y.equals(vertex.y);
    }

    @Override
    public int hashCode() {
        int result = edges != null ? edges.hashCode() : 0;
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (external ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String vdx_str = "";
        if (!x.d.equals(BigInteger.ONE)) {
            vdx_str = "/" + x.d;
        }
        String vdy_str = "";
        if (!y.d.equals(BigInteger.ONE)) {
            vdy_str = "/" + y.d;
        }
        return x.n + vdx_str + "," + y.n + vdy_str;
    }

}
