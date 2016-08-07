package com.teamdev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class Solution {
    public List<Vertex> sourcePositions;
    public List<Facet> facets;
    public List<Vertex> destinationPositions;
    public boolean isPerfect = false;

    public Solution(List<Vertex> sourcePositions, List<Facet> facets, List<Vertex> destinationPositions) {
        this.sourcePositions = sourcePositions;
        this.facets = facets;
        this.destinationPositions = destinationPositions;
    }

    public static Solution getBlindSquare() {
        List<Vertex> v = new ArrayList<>();
        v.add(new Vertex(new Fraction(0), new Fraction(0)));
        v.add(new Vertex(new Fraction(1), new Fraction(0)));
        v.add(new Vertex(new Fraction(1), new Fraction(1)));
        v.add(new Vertex(new Fraction(0), new Fraction(1)));
        List<Facet> facets = new ArrayList<>();
        List<Integer> fIndexes = new ArrayList<>();
        fIndexes.add(0);
        fIndexes.add(1);
        fIndexes.add(2);
        fIndexes.add(3);
        facets.add(new Facet(fIndexes));
        return new Solution(v, facets, v);
    }

//    public static Solution getSquareSolution() {
//
//    }

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

    public void save(File name) throws FileNotFoundException {
        String s = toStringFormat();
        Utils.writeStringToFile(name, s);
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

    public Solution normalize() {
        for (Vertex vertex : sourcePositions) {
            vertex.normalize();
        }

        for (Vertex vertex : destinationPositions) {
            vertex.normalize();
        }
        return this;
    }
}
