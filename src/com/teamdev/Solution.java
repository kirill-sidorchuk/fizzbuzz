package com.teamdev;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Solution {
    public List<Vertex> sourcePositions;
    public List<Facet> facets;
    public List<Vertex> destinationPositions;

    public Solution(List<Vertex> sourcePositions, List<Facet> facets, List<Vertex> destinationPositions) {
        this.sourcePositions = sourcePositions;
        this.facets = facets;
        this.destinationPositions = destinationPositions;
    }

    public String toStringFormat() {
        String result = "";
        result += sourcePositions.size() + "\n";
        for (Vertex vertex : sourcePositions) {
            result += vertex + "\n";
        }

        result += facets.size() + "\n";
        for (Facet facet : facets) {
            result += facet + "\n";
        }

        for (Vertex vertex : destinationPositions) {
            result += vertex + "\n";
        }
        return result;
    }

    public Solution rotate(double angle) {
        double[] R = new double[4];
        R[0] = Math.cos(angle);
        R[1] = -Math.sin(angle);
        R[2] = -R[1];
        R[3] = R[0];

//        List<Vertex> newSrc = new ArrayList<>();
//        for (Vertex vertex : sourcePositions) {
//            newSrc.add(vertex.rotate(R));
//        }

        List<Vertex> newDst = new ArrayList<>();
        for (Vertex vertex : destinationPositions) {
            newDst.add(vertex.rotate(R));
        }

        return new Solution(sourcePositions, facets, newDst);
    }

    public Solution translate(long nx, long dx, long ny, long dy) {
        Vertex T = new Vertex(nx, dx, ny, dy);
        List<Vertex> newDst = new ArrayList<>();
        for (Vertex vertex : destinationPositions) {
            newDst.add(vertex.translate(T));
        }
        return new Solution(sourcePositions, facets, newDst);
    }
}
