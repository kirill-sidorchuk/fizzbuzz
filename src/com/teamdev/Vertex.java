package com.teamdev;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Vertex {

    // x
    public long nX; // nominator
    public long dX; // denominator

    // y
    public long nY; // nominator
    public long dY; // denominator

    public Vertex(long nX, long dX, long nY, long dY) {
        this.nX = nX;
        this.dX = dX;
        this.nY = nY;
        this.dY = dY;
    }

    public Vertex(String line) {
        int i = line.indexOf(',');
        if( i == -1 ) throw new RuntimeException("Failed to parse line: " + line);

        String xLine = line.substring(0, i).trim();
        String yLine = line.substring(i+1).trim();

        i = xLine.indexOf('/');
        if( i == -1 ) {
            nX = Long.parseLong(xLine);
            dX = 1;
        }
        else {
            nX = Long.parseLong(xLine.substring(0, i).trim());
            dX = Long.parseLong(xLine.substring(i+1).trim());
        }

        i = yLine.indexOf('/');
        if( i == -1 ) {
            nY = Long.parseLong(yLine);
            dY = 1;
        }
        else {
            nY = Long.parseLong(yLine.substring(0, i).trim());
            dY = Long.parseLong(yLine.substring(i+1).trim());
        }
    }

    public float getFloatX() {
        return (float) ((double)nX / (double) dX);
    }

    public float getFloatY() {
        return (float) ((double)nY / (double) dY);
    }
}
