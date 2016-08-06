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
        Fraction fractionX2 = new Fraction(10, 1);
        Fraction fractionY1 = PaperFolderHelper.getYForLine(line, fractionX1);
        Fraction fractionY2 = PaperFolderHelper.getYForLine(line, fractionX2);
        List<Vertex> intersectionPoints = new ArrayList<>();
        Vertex point;
        for (int i = 0; i < polygon.vertices.size() - 1; i++) {
            Vertex vertex1 = polygon.vertices.get(i);
            Vertex vertex2 = polygon.vertices.get(i + 1);
            point = getLineInteractionsWrapper(vertex1.x, vertex2.x, vertex1.y, vertex2.y, fractionX1, fractionY1, fractionX2, fractionY2);
            if (point != null) {
                intersectionPoints.add(point);
            }

        }
        Vertex vertex1 = polygon.vertices.get(polygon.vertices.size()-1);
        Vertex vertex2 = polygon.vertices.get(0);
        point = getLineInteractionsWrapper(vertex1.x, vertex2.x, vertex1.y, vertex2.y, fractionX1, fractionY1, fractionX2, fractionY2);
        intersectionPoints.add(point);
        return intersectionPoints;
    }

    private Vertex getLineInteractionsWrapper(Fraction vertexX1, Fraction vertexX2, Fraction vertexY1, Fraction vertexY2, Fraction x1, Fraction y1, Fraction x2, Fraction y2) {
/*      Fraction vertexx1 = vertex1.getX();
        Fraction vertexy1 = vertex1.getFloatY();
        Fraction vertexx2 = vertex2.getFloatX();
        Fraction vertexy2 = vertex2.getFloatY();
        double dx1 = vertexx1 - vertexx2;
        double dx2 = x2 - x1;
        double dy1 = vertexy1 - vertexy2;
        double dy2 = y2 - y1;
        double x = dy1 * dx2 - dy2 * dx1;
        Point p1 = new Point(vertexx1, vertexy1);
        Point p2 = new Point(vertexx2, vertexy2);
        Point p3 = new Point(x1, y1);
        Point p4 = new Point(x2, y2);
        double koef1 = (p2.y - p1.y) / (p2.x - p1.x);
        double koef2 = (p4.x - p3.x) / (p4.y - p3.y);
        double y3 = (koef1 * koef2 * p3.y - koef1 * p3.x + koef1 * p1.x - p1.y) / (koef1 * koef2 - 1);
        double x3 = koef2 * y3 - koef2 * p3.y + p3.x;
        return getLineInteractions(p1, p2, p3, p4);*/

        try {
            Fraction koef1 = vertexY2.sub(vertexY1).div(vertexX2.sub(vertexX1));
            Fraction koef2 = x2.sub(x1).div(y2.sub(y1));
            Fraction y3 = (koef1.mul(koef2).mul(y1).sub(koef1.mul(x1).add(koef1.mul(vertexX1)).sub(vertexY1)).div(koef1.mul(koef2).sub(new Fraction(1, 1))));
            Fraction x3 = koef2.mul(y3).sub(koef2.mul(y1)).add(x1);
            return new Vertex(x3, y3);
        } catch (Exception e) {
            return null;
        }

    }

}
