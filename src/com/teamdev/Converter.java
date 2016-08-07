package com.teamdev;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Samsonov on 8/5/2016.
 */
public class Converter {

    private static class Fraction{
        BigDecimal n;
        BigDecimal d;

        public Fraction(BigDecimal n, BigDecimal d) {
            this.n = n;
            this.d = d;
        }
    }

    private static BigDecimal gcd(BigDecimal a, BigDecimal b){
        while(a.abs().compareTo(BigDecimal.ZERO) > 0 && b.abs().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal c = b;
            b = a.remainder(b);
            a = c;
        }
        return a.abs().add(b);
    }

    private static void simplifyFraction(Fraction f){
        BigDecimal multiplierX = gcd(f.n, f.d);
        f.n = f.n.divide(multiplierX);
        f.d = f.d.divide(multiplierX);
    }


    private static Fraction subFraction(Fraction f1, Fraction f2){
        BigDecimal n = f1.n.multiply(f2.d).subtract(f2.n.multiply(f1.d));
        BigDecimal d = f1.d.multiply(f2.d);
        Fraction resF = new Fraction(n, d);
        simplifyFraction(resF);
        return resF;
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
            Fraction x = subFraction(new Fraction(vertex.nXBig, vertex.dXBig), new Fraction(nXShift, dXShift));
            Fraction y = subFraction(new Fraction(vertex.nYBig, vertex.dYBig), new Fraction(nYShift, dYShift));

            vertex.x = new com.teamdev.Fraction(x.n.longValue(), x.d.longValue());
            vertex.y = new com.teamdev.Fraction(y.n.longValue(), y.d.longValue());
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

//private static void printVertex(){}

    public static void main(String[] args){
        Vertex v1 = new Vertex(new BigDecimal(1), new BigDecimal(2), new BigDecimal(-2), new BigDecimal(4));
        Vertex v2 = new Vertex(new BigDecimal(2), new BigDecimal(3), new BigDecimal(2), new BigDecimal(4));

        Fraction f = subFraction(new Fraction(v1.nXBig, v1.dXBig), new Fraction(v2.nXBig, v2.dXBig));

        System.out.println(f.n);
        System.out.println(f.d);

        /*List<Vertex> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);

        VertexShift vertexShift = calculateVertexShifts(list);

        System.out.println(vertexShift.nX);
        System.out.println(vertexShift.dX);
        System.out.println(vertexShift.nY);
        System.out.println(vertexShift.dY);

        System.out.println(gcd(new BigDecimal(8), new BigDecimal(12)));*/
    }


}
