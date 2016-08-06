package com.teamdev;

import java.util.ArrayList;
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

    public boolean containsPoint(Vertex X, List<Vertex> vertices) {
        Vertex A = vertices.get(i0);
        Vertex B = vertices.get(i1);

        Vertex BA = B.sub(A);
        Vertex XA = X.sub(A);

        if( XA.vect_prod_z(BA).n != 0 ) return false;

        Fraction s = XA.scalarMul(BA);
        if( s.n < 0 ) return false;

        return XA.normSquared().compareTo(BA.normSquared()) <= 0;
    }

    public Vertex getIntersection(Edge e, List<Vertex> vertices) {
        Vertex A = vertices.get(i0);
        Vertex B = vertices.get(i1);
        Vertex C = vertices.get(e.i0);
        Vertex D = vertices.get(e.i1);

        Vertex BA = B.sub(A);
        Vertex DC = D.sub(C);

        Fraction den = BA.vect_prod_z(DC);
        if( den.n == 0 ) {
            // coplanar lines
            return null;
        }

        Fraction alpha = A.vect_prod_z(DC).div(den).inv();
        if( alpha.n < 0 ) return null;

        if( alpha.n >= alpha.d ) return null;

        return A.add(BA.mul(alpha));
    }

    public static void main(String[] args) {
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(0,1, -1,1));
        vertices.add(new Vertex(1,1, 0,1));
        vertices.add(new Vertex(0,1, 1,1));
        vertices.add(new Vertex(-1,1, 0,1));

        Edge e1 = new Edge(0,2, false);
        Edge e2 = new Edge(1,3, false);

        Vertex intersection = e1.getIntersection(e2, vertices);
        intersection.toString();
    }
}
