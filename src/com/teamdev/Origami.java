package com.teamdev;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/6/2016.
 */
public class Origami {

    Origami parent = null;
    List<Vertex> vertices;
    List<Facet> facets;
    List<Edge> edges;

    public Origami() {
        vertices = new ArrayList<>();
        facets = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Origami(List<Vertex> vertices, List<Facet> facets, List<Edge> edges) {
        this.vertices = vertices;
        this.facets = facets;
        this.edges = edges;
    }

    public static Origami getSquare() {
        Origami origami = new Origami();
        origami.vertices.add(new Vertex(new Fraction(0), new Fraction(0)));
        origami.vertices.add(new Vertex(new Fraction(1), new Fraction(0)));
        origami.vertices.add(new Vertex(new Fraction(1), new Fraction(1)));
        origami.vertices.add(new Vertex(new Fraction(0), new Fraction(1)));

        ArrayList<Integer> facetList = new ArrayList<>();
        facetList.add(0);
        facetList.add(1);
        facetList.add(2);
        facetList.add(3);
        origami.facets.add(new Facet(facetList));

        origami.edges.add(new Edge(0,1));
        origami.edges.add(new Edge(1,2));
        origami.edges.add(new Edge(2,3));
        origami.edges.add(new Edge(3,0));

        for (Edge e : origami.edges) {
            origami.vertices.get(e.i0).addEdge(e);
            origami.vertices.get(e.i1).addEdge(e);
        }

        return origami;
    }

    public Origami copy() {
        Origami origami = new Origami();
        origami.vertices.addAll(vertices);
        origami.facets.addAll(facets);
        origami.edges.addAll(edges);
        return origami;
    }

    public Origami[] fold(FoldVector vector) {

        List<Vertex> intersections = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex x = edge.getIntersectionWithLine(vector, vertices);
            if( x != null )
                intersections.add(x);
        }
        if( intersections.size() > 2 ) throw new RuntimeException("non convex intersection found");
        if( intersections.size() != 2) throw new RuntimeException("there should be 2 intersections");

        List<Facet> tmpFacets = new ArrayList<>(facets);
        List<Edge> tmpEdges = new ArrayList<>(edges);
        Origami tmp = new Origami(vertices, tmpFacets, tmpEdges);

        int oldSize = vertices.size();

        tmp.vertices.addAll(intersections);

        Edge e = new Edge(oldSize, oldSize + 1);
        for (Vertex v : intersections) {
            v.addEdge(e);
        }

        tmp.edges.add(e);

        tmp.facets.clear();
        tmp.findIntersections();
        tmp.findFacets();

        // choosing which facets flip
        List<Facet> noFlipFacets = new ArrayList<>();
        List<Facet> flipFacets = new ArrayList<>();
        for (Facet facet : tmp.facets) {
            if(facet.isFlips(vector, tmp.vertices))
                flipFacets.add(facet);
            else
                noFlipFacets.add(facet);
        }

        List<Edge> noFlipEdges = tmp.findEdgesForFacets(noFlipFacets);
        List<Edge> flipEdges = tmp.findEdgesForFacets(flipFacets);

        Origami noFlipOrigami = new Origami(vertices, noFlipFacets, noFlipEdges);
        Origami flipOrigami = new Origami(vertices, flipFacets, flipEdges);

        noFlipOrigami.parent = this;
        flipOrigami.parent = this;

        return new Origami[]{noFlipOrigami, flipOrigami};
    }

    List<Edge> findEdgesForFacets(List<Facet> fs) {
        List<Edge> fsEdges = new ArrayList<>();
        for (Edge e : edges) {
            for (Facet f : fs) {
                if( f.vertexIndices.contains(e.i0) && f.vertexIndices.contains(e.i1) ) {
                    fsEdges.add(e);
                    break;
                }
            }
        }
        return fsEdges;
    }

    public void findIntersections() {
        int count = 1;
        while(count != 0) {
            count = 0;
            for (int i = 0; i < edges.size() && count == 0; ++i) {
                Edge ei = edges.get(i);
                for (int j = 0; j < edges.size() && count == 0; ++j) {
                    if( i == j ) continue;
                    Edge ej = edges.get(j);
                    if (ei.hasCommonVertex(ej)) continue;
                    Vertex intersection = ei.getIntersection(ej, vertices);
                    if (intersection != null) {
                        int intIndex = vertices.indexOf(intersection);
                        if (intIndex == -1) {
                            // adding to list
                            intIndex = vertices.size();
                            vertices.add(intersection);

                            splitEdge(ei, intersection, intIndex);
                            splitEdge(ej, intersection, intIndex);
                        } else {
                            intersection = vertices.get(intIndex);
                            if (ei.containsIndex(intIndex)) {
                                splitEdge(ej, intersection, intIndex);
                            } else {
                                splitEdge(ei, intersection, intIndex);
                            }
                        }
                        count++;
                    }
                }
            }
        }
    }

    private void splitEdge(Edge ej, Vertex intersection, int intIndex) {
        Vertex Bj0 = vertices.get(ej.i0);
        Vertex Bj1 = vertices.get(ej.i1);
        Bj0.edges.remove(ej);
        Bj1.edges.remove(ej);
        Edge ej0 = new Edge(ej.i0, intIndex, false);
        Edge ej1 = new Edge(intIndex, ej.i1, false);
        intersection.addEdge(ej0);
        intersection.addEdge(ej1);
        Bj0.edges.add(ej0);
        Bj1.edges.add(ej1);
        edges.remove(ej);
        edges.add(ej0);
        edges.add(ej1);
    }

    public void findFacets() {

        Set<Path> paths = new HashSet<>();

        for( int startIndex=0; startIndex<vertices.size(); ++startIndex) {

            Vertex _S = vertices.get(startIndex);
            for( int startEdgeIndex=0; startEdgeIndex < _S.edges.size(); ++startEdgeIndex ) {

                Path path = new Path();
                path.add(startIndex);

                int i = startIndex;
                Vertex S = vertices.get(i);
                Edge startEdge = S.edges.get(startEdgeIndex);

                boolean failed = false;
                while (true) {
                    int iN = startEdge.otherIndex(i);
                    if (iN == startIndex) break;

                    if( !path.add(iN) ) {
                        failed = true;
                        break;
                    }
                    Vertex N = vertices.get(iN);

                    Vertex NS = N.sub(S);

                    // search for left turn
                    double maxAngle = 0;
                    Edge bestEdge = null;
                    for (Edge edge : N.edges) {
                        if (edge.equals(startEdge)) continue;

                        int iN2 = edge.otherIndex(iN);
                        Vertex A = vertices.get(iN2);
                        Vertex AN = A.sub(N);
                        double sine = NS.getSinus(AN);
                        double dot = NS.scalarMul(AN).getDoubleValue();
                        double cosine = dot / Math.sqrt(AN.normSquared().getDoubleValue() * NS.normSquared().getDoubleValue());

                        double angle = Utils.getAngle(sine, cosine);
                        if (bestEdge == null || angle > maxAngle) {
                            maxAngle = angle;
                            bestEdge = edge;
                        }
                    }

                    i = iN;
                    S = N;
                    startEdge = bestEdge;
                }

                // validating facet
                if( !failed ) {
                    Fraction area = OPolygon.calcArea(path.indexes, vertices);
                    if (area.n.compareTo(BigInteger.ZERO) > 0) {
                        paths.add(path);
                    }
                }
            }
        }


        // converting
        for (Path p : paths) {
            Facet facet = new Facet(p.indexes);
            facets.add(facet);
        }
    }

}
