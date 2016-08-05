package com.teamdev.fold;

import com.teamdev.Vertex;

/**
 * @author Vladislav Kovchug
 */
public class FoldLine {
    private Vertex v1;
    private Vertex v2;

    public FoldLine(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }
}
