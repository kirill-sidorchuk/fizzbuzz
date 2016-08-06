package com.teamdev.triangulation;

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

    public List<IntersectionPoint> getIntersections(OPolygon polygon, FoldLine line) {
        double x1 = 0;
        double x2 = 10;
        double y1 = PaperFolderHelper.getYForLine(line, x1);
        double y2 = PaperFolderHelper.getYForLine(line, x2);
        List<IntersectionPoint> intersectionPoints = new ArrayList<>();
        IntersectionPoint point;
        for (int i = 0; i < polygon.vertices.size() - 1; i++) {
            point = getLineInteractions(polygon.vertices.get(i), polygon.vertices.get(i + 1), x1, y1, x2, y2);
            if (point != null) {
                intersectionPoints.add(point);
            }

        }
        point = getLineInteractions(polygon.vertices.get(polygon.vertices.size()), polygon.vertices.get(0), x1, y1, x2, y2);
        intersectionPoints.add(point);
        return intersectionPoints;
    }

    private IntersectionPoint getLineInteractions(Vertex vertex1, Vertex vertex2, double x1, double y1, double x2, double y2) {
        float vertexX1 = vertex1.getFloatX();
        float vertexY1 = vertex1.getFloatY();
        float vertexX2 = vertex2.getFloatX();
        float vertexY2 = vertex2.getFloatY();
        double dx1 = vertexX1 - vertexX2;
        double dx2 = x2 - x1;
        double dy1 = vertexY1 - vertexY2;
        double dy2 = y2 - y1;
        double x = dy1 * dx2 - dy2 * dx1;
        Point p1 = new Point(vertexX1, vertexY1);
        Point p2 = new Point(vertexX2, vertexY2);
        Point p3 = new Point(x1, y1);
        Point p4 = new Point(x2, y2);
        return null;
    }
}
    /*private */
