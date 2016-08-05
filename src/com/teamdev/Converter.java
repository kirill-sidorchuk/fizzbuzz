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

        final Comparator<Vertex> compX = (p1, p2) -> Double.compare(p1.nXBig.doubleValue() / p1.dXBig.doubleValue(), p2.nXBig.doubleValue()/ p2.dXBig.doubleValue());
        final Comparator<Vertex> compY = (p1, p2) -> Double.compare(p1.nYBig.doubleValue() / p1.dYBig.doubleValue(), p2.nYBig.doubleValue()/ p2.dYBig.doubleValue());

        nXShift = list.stream().min(compX).map(vertex -> vertex.nXBig).get();
        dXShift = list.stream().min(compX).map(vertex -> vertex.dXBig).get();

        nYShift = list.stream().min(compY).map(vertex -> vertex.nYBig).get();
        dYShift = list.stream().min(compY).map(vertex -> vertex.dYBig).get();

        for (Vertex vertex : list){
            vertex.nX = vertex.nXBig.subtract(nXShift).longValue();
            vertex.nY = vertex.nYBig.subtract(nYShift).longValue();

            vertex.dX = vertex.dXBig.longValue();
            vertex.dY = vertex.dYBig.longValue();

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
