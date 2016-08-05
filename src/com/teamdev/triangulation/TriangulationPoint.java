package com.teamdev.triangulation;

/**
 * @author : Sergey Pensov
 */
public class TriangulationPoint {
    public int x;
    public int y;
    public TriangulationPoint nextPoint;
    public int counter;


    public TriangulationPoint getNextPoint() {
        return nextPoint;
    }
}
