package com.teamdev;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Samsonov on 8/5/2016.
 */
public class Converter {

    private static BigDecimal gcd(BigDecimal a, BigDecimal b){
        while(a.compareTo(BigDecimal.ZERO) > 0 && b.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal c = b;
            b = a.remainder(b);
            a = c;
        }
        return a.add(b);
    }

    public static VertexShift calculateVertexShifts(List<Vertex> list){
        BigDecimal nXShift; // nominator
        BigDecimal dXShift = new BigDecimal(0); // denominator

        BigDecimal nYShift; // nominator
        BigDecimal dYShift = new BigDecimal(0); // denominator

        final Comparator<BigDecimal> comp = (p1, p2) -> p1.compareTo(p2);

        nXShift = list.stream().map(vertex -> vertex.nXBig).min(comp).get();
        nYShift = list.stream().map(vertex -> vertex.nYBig).min(comp).get();

        for (Vertex vertex : list){
            vertex.nX = vertex.nXBig.subtract(nXShift).longValue();
            vertex.nY = vertex.nYBig.subtract(nYShift).longValue();

            vertex.dX = vertex.dXBig.subtract(dXShift).longValue();
            vertex.dY = vertex.dYBig.subtract(dYShift).longValue();

            vertex.shifted = true;
        }

        return new VertexShift(nXShift, dXShift, nYShift, dYShift);
    }

    public static void shiftCoordinates(Problem problem){
        List<Vertex> list = new ArrayList<>();
        for (OPolygon polygon: problem.polygons){
            list.addAll(polygon.vertices);
        }
        for (LineSegment lineSegment: problem.lineSegments){
            list.add(lineSegment.v1);
            list.add(lineSegment.v2);
        }
        problem.vertexShift = Converter.calculateVertexShifts(list);
    }


    public static void main(String[] args){
        System.out.println(gcd(new BigDecimal(8),new BigDecimal(12)));
    }


}
