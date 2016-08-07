package com.teamdev;

import java.math.BigInteger;
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

    public Edge(int i0, int i1) {
        this.i0 = i0;
        this.i1 = i1;
        this.external = false;
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

    public boolean containsIndex(int i) {
        return i0 == i || i1 == i;
    }

    public boolean containsIndex(List<Integer> indices) {
        return indices.contains(i0) || indices.contains(i1);
    }

    public boolean containsPoint(Vertex X, List<Vertex> vertices) {
        Vertex A = vertices.get(i0);
        Vertex B = vertices.get(i1);

        Vertex BA = B.sub(A);
        Vertex XA = X.sub(A);

        if( !XA.vect_prod_z(BA).n.equals(BigInteger.ZERO) ) return false;

        Fraction s = XA.scalarMul(BA);
        if( s.n.compareTo(BigInteger.ZERO) < 0 ) return false;

        return XA.normSquared().compareTo(BA.normSquared()) <= 0;
    }

    public Vertex getIntersectionWithLine(FoldVector v, List<Vertex> vertices) {
        Vertex A = vertices.get(i0);
        Vertex B = vertices.get(i1);
        Vertex C = v.base;
        Vertex D = v.base.add(v.direction);

        Vertex BA = B.sub(A);
        Vertex DC = D.sub(C);

        Fraction den = BA.vect_prod_z(DC);
        if( den.n.equals(BigInteger.ZERO) ) {
            // coplanar lines
            return null;
        }

        Vertex CA = C.sub(A);
        Fraction alpha = CA.vect_prod_z(DC).div(den);
        if( alpha.n.compareTo(BigInteger.ZERO) < 0 ) return null;

        if( alpha.n.compareTo(alpha.d) > 0 ) return null;

        return A.add(BA.mul(alpha));
    }

    public Vertex getIntersectionWithLine(Edge e, List<Vertex> vertices) {
        return getIntersectionWithLine(FoldVector.fromVertices(vertices.get(e.i0), vertices.get(e.i1)), vertices);
    }

    public Vertex getIntersection(Edge e, List<Vertex> vertices) {
        Vertex x = getIntersectionWithLine(e, vertices);
        if( x == null ) return null;
        Vertex x2 = e.getIntersectionWithLine(this, vertices);
        if( x2 == null ) return null;
        if( !x.equals(x2) )
            throw new RuntimeException("intersection is not consistent");
        return x;
    }

    public static void main(String[] args) {
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(0,1, 0,1));
        vertices.add(new Vertex(0,1, 3,1));
        vertices.add(new Vertex(1,1, 2,1));
        vertices.add(new Vertex(3,1, 2,1));

        Edge e1 = new Edge(0,1, false);
        Edge e2 = new Edge(2,3, false);

        Vertex intersection = e1.getIntersection(e2, vertices);
        intersection.toString();
    }

    public int otherIndex(int i) {
        return i0 == i ? i1 : i0;
    }
}
