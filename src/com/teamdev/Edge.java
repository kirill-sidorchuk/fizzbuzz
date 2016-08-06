package com.teamdev;

import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/6/2016.
 */
public class Edge {
    public int i0;
    public int i1;
    public boolean external;

    public Edge(int i0, int i1, boolean external) {
        this.i0 = i0;
        this.i1 = i1;
        this.external = external;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return (i0 == edge.i0 && i1 == edge.i1) || (i0 == edge.i1 && i1 == edge.i0);
    }

    @Override
    public int hashCode() {
        int result = i0;
        result = 31 * result + i1;
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" + i0 + ", " + i1 + "}";
    }

    public boolean hasCommonVertex(Edge e) {
        return i0 == e.i0 || i0 == e.i1 || i1 == e.i0 || i1 == e.i1;
    }

    public boolean intersects(Edge e, List<Vertex> vertices) {
        Vertex A = vertices.get(i0);
        Vertex B = vertices.get(i1);
        Vertex C = vertices.get(e.i0);
        Vertex D = vertices.get(e.i1);

        Vertex BA = B.sub(A);
        Vertex DC = D.sub(C);
//        A.vect_prod_z(DC)
        return false;
    }
}
