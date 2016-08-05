package com.teamdev;

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

    @Override
    public String toString() {
        String result = "";
        result += sourcePositions.size() + "\n";
        for (Vertex vertex : sourcePositions) {
            result += vertex.toString() + "\n";
        }

        result += facets.size() + "\n";
        for (Facet facet : facets) {
            result += facet.toString() + "\n";
        }

        for (Vertex vertex : destinationPositions) {
            result += vertex.toString() + "\n";
        }
        return result;
    }
}
