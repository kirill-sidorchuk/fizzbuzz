package com.teamdev.triangulatio;

/**
 * @author : Sergey Pensov
 */
public class Point {
    private float x;
    private float y;


    public Point(float floatX, float floatY) {
        this.x = floatX;
        this.y = floatY;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
