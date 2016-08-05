package com.teamdev;

import sun.plugin.dom.exception.InvalidStateException;

import java.math.BigDecimal;

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

    public Vertex(BigDecimal nX, BigDecimal dX, BigDecimal nY, BigDecimal dY) {
        this.nXBig = nX;
        this.dXBig = dX;
        this.nYBig = nY;
        this.dYBig = dY;
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
}
