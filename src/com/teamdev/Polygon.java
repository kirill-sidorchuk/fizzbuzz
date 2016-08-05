package com.teamdev;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Polygon {

    public List<Vertex> vertices = new ArrayList<>();

    @Override
    public String toString() {
        String result = "";
        result += "Polygon vertices:\n";
        for (Vertex v: vertices) {
            String vdx_str = "";
            if (v.dX != 1) {
                vdx_str = "/" + v.dX;
            }
            String vdy_str = "";
            if (v.dY != 1) {
                vdy_str = "/" + v.dY;
            }
            result += v.nX + vdx_str + "," + v.nY + vdy_str + "\n";
        }
        return result;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}
