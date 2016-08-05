package com.teamdev.triangulation;

import com.teamdev.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Sergey Pensov
 */

/*
* Вообще хер его, как я потом пойму, что оно делает, но вроде как-то так
*
*
* */
public class PolygonHelper {
    public static int counter = 0;

    public void createBufferedImageFromVertices(OPolygon polygon) {
        ImageData maxPoint = getMaxAndMinPoint(polygon);
        BufferedImage bufferedImage = new BufferedImage((int) (maxPoint.getMaxX() - maxPoint.getMinX()), (int) (maxPoint.getMaxY() - maxPoint.getMinY()), BufferedImage.TYPE_BYTE_GRAY);
        int polySize = polygon.vertices.size();
        Graphics2D graphics2D = bufferedImage.createGraphics();
        int[] xpoint = new int[polySize];
        int[] ypoint = new int[polySize];
        List<Vertex> vertices = polygon.vertices;
        for (int i = 0; i < polySize; i++) {
            xpoint[i] = (int) (ProblemVisualizer.xToPlot(vertices.get(i).getFloatX()) - maxPoint.getMinX());
            ypoint[i] = (int) (ProblemVisualizer.yToPlot(vertices.get(i).getFloatY()) - maxPoint.getMinY());
        }
        List<TrianglePolygon> trianglePolygons = new ArrayList<>();
        System.out.println(getPolygonArea(xpoint, ypoint, polySize, trianglePolygons));
        for (int i = 0; i < trianglePolygons.size(); i++) {
            int[] polyX = new int[3];
            int[] polyY = new int[3];
            polyX[0] = trianglePolygons.get(i).x1;
            polyX[1] = trianglePolygons.get(i).x2;
            polyX[2] = trianglePolygons.get(i).x3;
            polyY[0] = trianglePolygons.get(i).y1;
            polyY[1] = trianglePolygons.get(i).y2;
            polyY[2] = trianglePolygons.get(i).y3;
            if (i % 3 == 0) {
                graphics2D.setColor(Color.red);
                graphics2D.fillPolygon(polyX, polyY, 3);
            } else if (i % 3 == 1) {
                graphics2D.setColor(Color.green);
                graphics2D.fillPolygon(polyX, polyY, 3);
            } else {
                graphics2D.setColor(Color.blue);
                graphics2D.fillPolygon(polyX, polyY, 3);
            }
        }
        File file = new File("poly/" + Main.task + "." + counter + ".png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        counter++;
    }

    private ImageData getMaxAndMinPoint(OPolygon polygon) {
        float maxX = 0;
        float maxY = 0;
        //todo: change this value ASAP
        float minX = 1000000;
        float minY = 1000000;
        float vertexX = 0;
        float vertexY = 0;

        for (Vertex vertex : polygon.vertices) {
            vertexX = ProblemVisualizer.xToPlot(vertex.getFloatX());
            vertexY = ProblemVisualizer.yToPlot(vertex.getFloatY());
            if (vertexX > maxX) {
                maxX = vertexX;
            }
            if (vertexY > maxY) {
                maxY = vertexY;
            }
            if (vertexX < minX) {
                minX = vertexX;
            }
            if (vertexY < minY) {
                minY = vertexY;
            }
        }
        return new ImageData(maxX, maxY, minX, minY);
    }

    public float getPolygonArea(int[] xpoint, int[] ypoint, int polySize, List<TrianglePolygon> trianglePolygons) {
        trianglePolygons.addAll(getTriangles(createTriangulationPoint(xpoint, ypoint, polySize)));
        double square = 0;
        for (TrianglePolygon polygon : trianglePolygons) {
            double x1 = polygon.x1;
            double x2 = polygon.x2;
            double x3 = polygon.x3;
            double y1 = polygon.y1;
            double y2 = polygon.y2;
            double y3 = polygon.y3;
            double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            double b = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
            double c = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
            if (a + b <= c || a + c <= b || a + c <= b)
                throw new IllegalArgumentException("У Сережи кривые руки");
            else {
                double p = (a + b + c) / 2.0;
                square += Math.sqrt(p * (p - a) * (p - b) * (p - c));
                System.out.println("Площадь: " + square);
            }
        }
        return 0;
    }

    private TriangulationPoint createTriangulationPoint(int[] xpoint, int[] ypoint, int polySize) {
        TriangulationPoint start = new TriangulationPoint();
        start.x = xpoint[polySize - 1];
        start.y = ypoint[polySize - 1];
        TriangulationPoint next = start;
        for (int i = polySize - 2; i >= 0; i--) {
            next.nextPoint = new TriangulationPoint();
            next.nextPoint.x = xpoint[i];
            next.nextPoint.y = ypoint[i];
            next = next.nextPoint;
        }
        next.nextPoint = start;
        return start;
    }

    public double det(int x1, int y1, int x2, int y2, int x3, int y3) {
        return (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
    }

    public boolean inTriangle(int x, int y, int x1, int y1, int x2, int y2, int x3, int y3) {
        double a = det(x, y, x1, y1, x2, y2);
        double b = det(x, y, x2, y2, x3, y3);
        double c = det(x, y, x3, y3, x1, y1);
        if (((a > 0) && (b > 0) && (c > 0)) || ((a < 0) && (b < 0) && (c < 0))) {
            return true;
        } else {
            return false;
        }
    }

    public List<TrianglePolygon> getTriangles(TriangulationPoint triangulationPoint) {
        List<TrianglePolygon> trianglePolygons = new ArrayList<>();
        TriangulationPoint point1 = triangulationPoint;
        TriangulationPoint point2 = point1.getNextPoint();
        TriangulationPoint point3 = point2.getNextPoint();

        while (point3.getNextPoint() != point1) {
            boolean inPoint = false;
            TriangulationPoint node = point3.getNextPoint();
            while (node != point1) {
                if (inTriangle(node.x, node.y, point1.x, point1.y, point2.x, point2.y, point3.x, point3.y)) {
                    inPoint = true;
                }
                node = node.getNextPoint();
            }
            if ((det(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y) > 0 && !inPoint)) {
                deleteTrianglePoint(point2, triangulationPoint);
                trianglePolygons.add(new TrianglePolygon(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y));
                point2 = point1.nextPoint;
                point3 = point2.nextPoint;
            } else {
                point1 = point1.nextPoint;
                point2 = point1.nextPoint;
                point3 = point2.nextPoint;
            }
        }
        trianglePolygons.add(new TrianglePolygon(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y));
        return trianglePolygons;
    }

    private boolean deleteTrianglePoint(TriangulationPoint node, TriangulationPoint point) {
        TriangulationPoint last;
        TriangulationPoint elem;
        if ((point.getNextPoint() == point) && (point == node)) {
            return false;
        } else {
            if (node == point) {
                last = point;
                while (last.getNextPoint() != point)
                    last = last.getNextPoint();
                last.nextPoint = point.nextPoint;
            } else {
                elem = point;
                while (elem.getNextPoint() != node)
                    elem = elem.getNextPoint();
                elem.nextPoint = node.nextPoint;
            }
        }
        return true;
    }
}
