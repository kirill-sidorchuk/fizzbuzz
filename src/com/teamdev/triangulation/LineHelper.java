package com.teamdev.triangulation;

import com.teamdev.Fraction;
import com.teamdev.OPolygon;
import com.teamdev.Vertex;
import com.teamdev.fold.FoldLine;
import com.teamdev.fold.PaperFolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Sergey Pensov
 */
public class LineHelper {

    public List<Vertex> getIntersections(OPolygon polygon, FoldLine line) {
        Fraction fractionX1 = new Fraction(0, 1);
        Fraction fractionX2 = new Fraction(100, 1);
        Fraction fractionY1 = PaperFolderHelper.getYForLine(line, fractionX1);
        Fraction fractionY2 = PaperFolderHelper.getYForLine(line, fractionX2);
        List<Vertex> intersectionPoints = new ArrayList<>();
        Vertex point;
        for (int i = 0; i < polygon.vertices.size() - 1; i++) {
            Vertex vertex1 = polygon.vertices.get(i);
            Vertex vertex2 = polygon.vertices.get(i + 1);
            point = Intersection(vertex1.x, vertex2.x, vertex1.y, vertex2.y, fractionX1, fractionY1, fractionX2, fractionY2, false);
            if (point != null) {
                if (!intersectionPoints.contains(point))
                    intersectionPoints.add(point);
            }

        }
        if (polygon.vertices.size() == 0) return new ArrayList<>();
        Vertex vertex1 = polygon.vertices.get(polygon.vertices.size() - 1);
        Vertex vertex2 = polygon.vertices.get(0);
        point = Intersection(vertex1.x, vertex2.x, vertex1.y, vertex2.y, fractionX1, fractionY1, fractionX2, fractionY2, false);
        if (point != null)
            if (!intersectionPoints.contains(point))
                intersectionPoints.add(point);
        if (intersectionPoints.size() == 2) {
            if (intersectionPoints.get(0).equals(intersectionPoints.get(1)))
                return new ArrayList<>();
        }
        return intersectionPoints;
    }

    public void getIntersectionsPolygonsLine(OPolygon polygon, FoldLine line,List<Vertex> intersectionPoints) {
        Fraction fractionX1 = line.bottomPoint.x;
        Fraction fractionY1 = line.bottomPoint.y;
        Fraction fractionX2 = line.topPoint.x;
        Fraction fractionY2 = line.topPoint.y;
        PolygonHelper polygonHelper = new PolygonHelper();
        Vertex point;
        for (int i = 0; i < polygon.vertices.size() - 1; i++) {
            Vertex vertex1 = polygon.vertices.get(i);
            Vertex vertex2 = polygon.vertices.get(i + 1);
            point = Intersection(vertex1.x, vertex2.x, vertex1.y, vertex2.y, fractionX1, fractionY1, fractionX2, fractionY2, true);
            if (point != null) {
                if (!intersectionPoints.contains(point)) {
                    if (polygonHelper.isPointInside(polygon, line.bottomPoint)) {
                        if (!intersectionPoints.contains(line.bottomPoint))
                            intersectionPoints.add(line.bottomPoint);
                        intersectionPoints.add(point);
                    }
                }
                if (polygonHelper.isPointInside(polygon, line.topPoint)) {
                    if (!intersectionPoints.contains(line.topPoint))
                        intersectionPoints.add(line.topPoint);


                } else {
                    if (polygonHelper.isPointInside(polygon, line.bottomPoint)) {
                        if (!intersectionPoints.contains(line.bottomPoint))
                            intersectionPoints.add(line.bottomPoint);
                        if (!intersectionPoints.contains(line.topPoint))
                            intersectionPoints.add(line.topPoint);
                    }
                }
            }
        }
        if (polygon.vertices.size() == 0) return;
        Vertex vertex1 = polygon.vertices.get(polygon.vertices.size() - 1);
        Vertex vertex2 = polygon.vertices.get(0);
        point = Intersection(vertex1.x, vertex2.x, vertex1.y, vertex2.y, fractionX1, fractionY1, fractionX2, fractionY2, true);
        if (point != null)
            if (!intersectionPoints.contains(point))
                intersectionPoints.add(point);
    }

    private static Vertex Intersection(Fraction xo, Fraction xb,/**/ Fraction yo, Fraction yb,/**/ Fraction x1, Fraction y1,/**/ Fraction x2, Fraction y2, boolean isPolygonLine) {
        Fraction p = xb.sub(xo);
        Fraction q = yb.sub(yo);
        Fraction r = new Fraction(0, 0);
        Fraction zo = new Fraction(0, 0);
        Fraction p1 = x2.sub(x1);
        Fraction q1 = y2.sub(y1);
        Fraction r1 = new Fraction(0, 0);


        Fraction x = xo.mul(q).mul(p1).sub(x1.mul(q1).mul(p)).sub(yo.mul(p).mul(p1)).add(y1.mul(p).mul(p1)).div(q.mul(p1).sub(q1.mul(p)));
        Fraction y = yo.mul(p).mul(q1).sub(y1.mul(p1).mul(q)).sub(xo.mul(q).mul(q1)).add(x1.mul(q).mul(q1)).div(p.mul(q1).sub(p1.mul(q)));

        Fraction maxX;
        Fraction maxY;
        Fraction minX;
        Fraction minY;
        if (x.d == 0 || y.d == 0) return null;

        if (!isPolygonLine) {
            maxX = xo.getDoubleValue() > xb.getDoubleValue() ? xo : xb;
            minX = xo.getDoubleValue() < xb.getDoubleValue() ? xo : xb;
            maxY = yo.getDoubleValue() > yb.getDoubleValue() ? yo : yb;
            minY = yo.getDoubleValue() < yb.getDoubleValue() ? yo : yb;
            if (x.getDoubleValue() > maxX.getDoubleValue() || x.getDoubleValue() < minX.getDoubleValue() ||
                    y.getDoubleValue() > maxY.getDoubleValue() || y.getDoubleValue() < minY.getDoubleValue())
                return null;
        } else {
            Fraction maxX1;
            Fraction maxY1;
            Fraction minX1;
            Fraction minY1;
            maxX = xo.getDoubleValue() > xb.getDoubleValue() ? xo : xb;
            minX = xo.getDoubleValue() < xb.getDoubleValue() ? xo : xb;
            maxY = yo.getDoubleValue() > yb.getDoubleValue() ? yo : yb;
            minY = yo.getDoubleValue() < yb.getDoubleValue() ? yo : yb;
            maxX1 = x1.getDoubleValue() > x2.getDoubleValue() ? x1 : x2;
            minX1 = x1.getDoubleValue() < x2.getDoubleValue() ? x1 : x2;
            maxY1 = y1.getDoubleValue() > y1.getDoubleValue() ? y1 : y2;
            minY1 = y1.getDoubleValue() < y1.getDoubleValue() ? y1 : y2;
            if (x.getDoubleValue() > maxX.getDoubleValue() || x.getDoubleValue() < minX.getDoubleValue() ||
                    y.getDoubleValue() > maxY.getDoubleValue() || y.getDoubleValue() < minY.getDoubleValue() ||
                    x.getDoubleValue() > maxX1.getDoubleValue() || x.getDoubleValue() < minX1.getDoubleValue() ||
                    y.getDoubleValue() > maxY1.getDoubleValue() || y.getDoubleValue() < minY1.getDoubleValue())
                return null;
        }

        return new Vertex(x, y);
    }

}
