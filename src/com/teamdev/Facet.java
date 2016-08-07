package com.teamdev;

import java.util.ArrayList;
import java.util.List;

public class Facet {
    public List<Integer> vertexIndices;

    public Facet(List<Integer> vertexIndices) {
        this.vertexIndices = vertexIndices;
    }

    public Facet() {
        vertexIndices = new ArrayList<>();
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

    public float[] getXs(List<Vertex> vertices) {
        float[] xs = new float[vertexIndices.size()];
        for( int i=0; i<vertexIndices.size(); ++i) {
            int vertIndex = vertexIndices.get(i);
            xs[i] = vertices.get(vertIndex).getFloatX();
        }
        return xs;
    }

    public float[] getYs(List<Vertex> vertices) {
        float[] ys = new float[vertexIndices.size()];
        for( int i=0; i<vertexIndices.size(); ++i) {
            int vertIndex = vertexIndices.get(i);
            ys[i] = vertices.get(vertIndex).getFloatY();
        }
        return ys;
    }

    public boolean isFlips(FoldVector f, List<Vertex> vertices) {
        for (Integer index : vertexIndices) {
            Vertex V = vertices.get(index);
            if( f.isFlips(V)) return true;
        }
        return false;
    }
}
