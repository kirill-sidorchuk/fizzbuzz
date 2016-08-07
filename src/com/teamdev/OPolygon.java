package com.teamdev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class OPolygon {

    private FRange xRange;
    private FRange yRange;
    private Vertex center;

    public List<Vertex> vertices = new ArrayList<>();

    public OPolygon() {
    }

    public OPolygon(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public FRange getXRange() {
        if( xRange == null ) calcRanges();
        return xRange;
    }

    public FRange getYRange() {
        if( yRange == null ) calcRanges();
        return yRange;
    }

    private void calcRanges() {
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for (Vertex vertex : vertices) {
            float x = vertex.getFloatX();
            float y = vertex.getFloatY();

            if (minX > x) minX = x;
            if (minY > y) minY = y;
            if (maxX < x) maxX = x;
            if (maxY < y) maxY = y;
        }

        xRange = new FRange(minX, maxX);
        yRange = new FRange(minY, maxY);
    }

    public Vertex getCenter() {
        if( center == null ) {
            center = Vertex.average(vertices);
        }
        return center;
    }

    public String toStringFormat() {
        String result = "";
        result += vertices.size() + "\n";
        for (Vertex v: vertices) {
            result += v + "\n";
        }
        return result;
    }

    public static Fraction calcArea(List<Integer> indexes, List<Vertex> vertices) {
        Fraction area = new Fraction(0,1);
        int j = indexes.size()-1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<indexes.size(); i++)
        {
            int iIndex = indexes.get(i);
            int jIndex = indexes.get(j);
            final Vertex vi = vertices.get(iIndex);
            final Vertex vj = vertices.get(jIndex);
            area = area.add((vj.x.add(vi.x)).mul(vj.y.sub(vi.y))).normalize();
            j = i;  //j is previous vertex to i
        }
        return area.div(2).inv().normalize();
    }

    public Fraction calcArea() {
        Fraction area = new Fraction(0,1);
        int j = vertices.size()-1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<vertices.size(); i++)
        {
            final Vertex vi = vertices.get(i);
            final Vertex vj = vertices.get(j);
            area = area.add((vj.x.add(vi.x)).mul(vj.y.sub(vi.y))).normalize();
            j = i;  //j is previous vertex to i
        }
        return area.div(2).inv().normalize();
    }

    public void moveToCenter(Vertex newCenter) {
        Vertex center = getCenter();
        System.out.println(center);
        System.out.println(newCenter);
        Fraction xShift = newCenter.x.sub(center.x);
        Fraction yShift = newCenter.y.sub(center.y);
        System.out.println(xShift);
        System.out.println(yShift);
        Vertex shiftVertex = new Vertex(xShift, yShift);

        this.vertices = this.vertices.stream().map(vertex -> vertex.add(shiftVertex)).collect(Collectors.toList());
        this.center = newCenter;
    }
}
