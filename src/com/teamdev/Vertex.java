package com.teamdev;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Vertex {

    // x
    public int nX; // nominator
    public int dX; // denominator

    // y
    public int nY; // nominator
    public int dY; // denominator

    public Vertex(int nX, int dX, int nY, int dY) {
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
            nX = Integer.parseInt(xLine);
            dX = 1;
        }
        else {
            nX = Integer.parseInt(xLine.substring(0, i).trim());
            dX = Integer.parseInt(xLine.substring(i+1).trim());
        }

        i = yLine.indexOf('/');
        if( i == -1 ) {
            nY = Integer.parseInt(yLine);
            dY = 1;
        }
        else {
            nY = Integer.parseInt(yLine.substring(0, i).trim());
            dY = Integer.parseInt(yLine.substring(i+1).trim());
        }
    }

    public float getFloatX() {
        return (float)nX / (float)dX;
    }

    public float getFloatY() {
        return (float)nY / (float)dY;
    }
}
