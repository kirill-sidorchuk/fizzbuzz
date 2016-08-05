package com.teamdev;

import sun.plugin.dom.exception.InvalidStateException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Vertex {

    boolean shifted = false;
    // x
    public long nX; // nominator
    public long dX; // denominator

    // y
    public long nY; // nominator
    public long dY; // denominator

    // x in Big Decimal
    public BigDecimal nXBig; // nominator
    public BigDecimal dXBig; // denominator

    // y in Big Decimal
    public BigDecimal nYBig; // nominator
    public BigDecimal dYBig; // denominator

    public Vertex() {
        nX = 0;
        dX = 1;
        nY = 0;
        dY = 1;
        nXBig = new BigDecimal(0);
        dXBig = new BigDecimal(1);
        nYBig = new BigDecimal(0);
        dYBig = new BigDecimal(1);
    }

    public Vertex(BigDecimal nX, BigDecimal dX, BigDecimal nY, BigDecimal dY) {
        this.nXBig = nX;
        this.dXBig = dX;
        this.nYBig = nY;
        this.dYBig = dY;
    }


    public Vertex(long nX, long dX, long nY, long dY) {
        this.nX = nX;
        this.dX = dX;
        this.nY = nY;
        this.dY = dY;
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
        nX = nXBig.longValue();
        dX = dXBig.longValue();
        nY = nYBig.longValue();
        dY = dYBig.longValue();
        shifted = true;
    }

    public Vertex translate(Vertex v) {
        long _nX = nX*v.dX + v.nX*dX;
        long _dX = v.dX * dX;
        long _nY = nY*v.dY + v.nY*dY;
        long _dY = v.dY * dY;

        return new Vertex(_nX, _dX, _nY, _dY);
        // todo add big decimals
    }

    public Vertex rotate(double[] R) {
        float fx = getFloatX();
        float fy = getFloatY();

        double rx = R[0] * fx + R[1] * fy;
        double ry = R[2] * fx + R[3] * fy;

        final long ACC = 80000000;
        return new Vertex( Math.round(rx*ACC), ACC, Math.round(ry*ACC), ACC);
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

        return new Vertex(Math.round(ACC*x), den, Math.round(y*ACC), den);
    }

    public long common_divider(long a, long b) {
        if( (a % 2) == 0 && (b % 2) == 0 ) return 2;
        if( (a % 3) == 0 && (b % 3) == 0 ) return 3;
        if( (a % 5) == 0 && (b % 5) == 0 ) return 5;
        if( (a % 7) == 0 && (b % 7) == 0 ) return 7;
        if( (a % 11) == 0 && (b % 11) == 0 ) return 11;
        if( (a % 13) == 0 && (b % 13) == 0 ) return 13;
        if( (a % 17) == 0 && (b % 17) == 0 ) return 17;
        return 1;
    }

    public void normalize() {
        if( nX == 0 ) {
            dX = 1;
        }
        else
            for( int i=0; i<20; ++i) {
                long d = common_divider(nX, dX);
                if (d == 1) break;
                nX /= d;
                dX /= d;
            }

        if( nY == 0 ) {
            dY = 1;
        }
        else
            for( int i=0; i<20; ++i) {
                long d = common_divider(nY, dY);
                if (d == 1) break;
                nY /= d;
                dY /= d;
            }
    }

    private void checkShifted(){
        if (!shifted) {
            throw new InvalidStateException("Vertex is not shifted");
        }
    }

    public float getFloatX() {
        checkShifted();
        return (float) ((double)nX / (double) dX);
    }

    public float getFloatY() {
        checkShifted();
        return (float) ((double)nY / (double) dY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (nX != vertex.nX) return false;
        if (dX != vertex.dX) return false;
        if (nY != vertex.nY) return false;
        if (dY != vertex.dY) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (nX ^ (nX >>> 32));
        result = 31 * result + (int) (dX ^ (dX >>> 32));
        result = 31 * result + (int) (nY ^ (nY >>> 32));
        result = 31 * result + (int) (dY ^ (dY >>> 32));
        return result;
    }

    @Override
    public String toString() {
        String vdx_str = "";
        if (dX != 1) {
            vdx_str = "/" + dX;
        }
        String vdy_str = "";
        if (dY != 1) {
            vdy_str = "/" + dY;
        }
        return nX + vdx_str + "," + nY + vdy_str;
    }
}
