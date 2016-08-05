package com.teamdev.fold;

import com.teamdev.Vertex;

import java.util.List;

/**
 * @author Vladislav Kovchug
 */
public class Paper {
    private List<List<Vertex>> vertices;

    public Paper(List<List<Vertex>> vertices) {
        this.vertices = vertices;
    }

    public List<List<Vertex>> getVertices() {
        return vertices;
    }

    public void setVertices(List<List<Vertex>> vertices) {
        this.vertices = vertices;
    }

    public void add(List<Vertex> polygon){
        this.vertices.add(polygon);
    }

}
