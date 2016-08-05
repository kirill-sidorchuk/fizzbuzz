package com.teamdev.triangulatio;

import com.teamdev.Main;
import com.teamdev.OPolygon;
import com.teamdev.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * @author : Sergey Pensov
 */
public class PolygonHelper {
    public static int counter = 0;

    public void createBufferedImageFromVertices(OPolygon polygon) {
        Point maxPoint = getMaxPoint(polygon);
        BufferedImage bufferedImage = new BufferedImage((int) (maxPoint.getX() * 500), (int) (maxPoint.getY() * 500), BufferedImage.TYPE_BYTE_GRAY);
        int polySize = polygon.vertices.size();
        Graphics2D graphics2D = bufferedImage.createGraphics();
        int[] xpoint = new int[polySize];
        int[] ypoint = new int[polySize];
        List<Vertex> vertices = polygon.vertices;
        for (int i = 0; i < polySize; i++) {
            xpoint[i] = (int) vertices.get(i).getFloatX() * 500;
            ypoint[i] = (int) vertices.get(i).getFloatY() * 500;
        }
        graphics2D.drawPolygon(xpoint, ypoint, polySize);
        File file = new File("poly/" + Main.task + "." + counter + ".png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        counter++;
    }

    private Point getMaxPoint(OPolygon polygon) {
        float maxX = 0;
        float maxY = 0;
        float vertexX = 0;
        float vertexY = 0;
        for (Vertex vertex : polygon.vertices) {
            vertexX = vertex.getFloatX();
            vertexY = vertex.getFloatX();
            if (vertexX > maxX) {
                maxX = vertexX;
            }
            if (vertexY > maxY) {
                maxY = vertexY;
            }
        }

        return new Point(maxX, maxY);


    }
}
