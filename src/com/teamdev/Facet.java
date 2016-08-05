package com.teamdev;

import java.util.List;

public class Facet {
    public List<Integer> vertexIndices;

    public Facet(List<Integer> vertexIndices) {
        this.vertexIndices = vertexIndices;
    }

    @Override
    public String toString() {
        String result = "";
        result += vertexIndices.size() + " ";
        for (Integer vertexIndex : vertexIndices) {
            result += vertexIndex + " ";
        }
        return result;
    }
}
