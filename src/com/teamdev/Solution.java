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
}
