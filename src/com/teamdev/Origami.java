package com.teamdev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/6/2016.
 */
public class Origami {

    int nContourVertices;
    List<Vertex> vertices = new ArrayList<>();
    List<Facet> facets = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();

    private static class Path {
        List<Integer> indexes = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        boolean abandoned = false;

        public Path() {
        }

        public Path(List<Integer> indexes) {
            this.indexes = indexes;
            set = new HashSet<>(indexes);
        }

        public boolean contains(int i) {
            return set.contains(i);
        }

        public boolean add(int i) {
            if( !contains(i)) {
                indexes.add(i);
                set.add(i);
                return true;
            }
            return false;
        }

        public Path copy() {
            Path p = new Path();
            p.indexes.addAll(indexes);
            p.set.addAll(set);
            return p;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Path path = (Path) o;
            return set.equals(path.set);
        }

        @Override
        public int hashCode() {
            return set.hashCode();
        }
    }

    public void findFacetsOld() {
        // searching for facets
        Set<Path> foundFacets = new HashSet<>();
        for( int iStart=0; iStart<vertices.size(); ++iStart) {
            if( vertices.get(iStart).edges == null || vertices.get(iStart).edges.size() == 0 )
                continue;
            Path mainPath = new Path();
            Set<Path> addedPaths = new HashSet<>();
            traverseOld(iStart, -1, mainPath, vertices, addedPaths);
            if (!mainPath.abandoned) addedPaths.add(mainPath);

            // selecting tightest facet
            int minCount = Integer.MAX_VALUE;
            Path bestPath = null;
            for (Path path : addedPaths) {
                if( path.abandoned) continue;
                if( path.set.size() < minCount) {
                    minCount = path.set.size();
                    bestPath = path;
                }
            }

            if( bestPath == null) throw new RuntimeException("could not find path for start point " + iStart);
            foundFacets.add(bestPath);
        }
        System.out.printf("" + foundFacets.size());

        // converting
        for (Path p : foundFacets) {
            Facet facet = new Facet(p.indexes);
            facets.add(facet);
        }

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
                    if (area.n > 0) {
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

    private void traverseOld(int iStart, int iFromWhere, Path path, List<Vertex> vertices, Set<Path> foundFacets) {

        // adding self to set
        if( path.contains(iStart) ) return;
        path.add(iStart);

        // selecting possible next edges
        List<Edge> nextEdges = new ArrayList<>();
        List<Edge> startEdges = vertices.get(iStart).edges;
        Edge originEdge = new Edge(iStart, iFromWhere, false);
        for (Edge edge : startEdges) {
            if( edge.equals(originEdge) ) continue;
            nextEdges.add(edge);
        }

        if( nextEdges.size() == 1) {
            Edge nextEdge = nextEdges.get(0);
            int nextI = nextEdge.i0 == iStart ? nextEdge.i1 : nextEdge.i0;
            traverseOld(nextI, iStart, path, vertices, foundFacets);
        }
        else {
            path.abandoned = true;
            for (Edge nextEdge : nextEdges) {
                Path pathCopy = path.copy();
                int nextI = nextEdge.i0 == iStart ? nextEdge.i1 : nextEdge.i0;
                traverseOld(nextI, iStart, pathCopy, vertices, foundFacets);
                foundFacets.add(pathCopy);
            }
        }
    }

}
