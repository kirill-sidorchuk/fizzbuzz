package com.teamdev;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolutionVisualizer {
    public static final int IMAGE_WIDTH = 1024;
    public static final int IMAGE_HEIGHT = 768;

    public static final int GRAPHIC_WIDTH = 600;
    public static final int GRAPHIC_HEIGHT = 600;

    public static final float MAX_SIZE = (float) Math.sqrt(2);

    private static int xToPlot(float x) {
        if (x < 0 || x > MAX_SIZE) {
            throw new IllegalArgumentException("value of x expected to be in range [0:sqrt(2)], actual value: " + x);
        }
        int borderWidth = (IMAGE_WIDTH - GRAPHIC_WIDTH) / 2;
        return borderWidth + (int) (GRAPHIC_WIDTH * x / MAX_SIZE);
    }

    private static int yToPlot(float y) {
        if (y < 0 || y > MAX_SIZE) {
            throw new IllegalArgumentException("value of y expected to be in range [0:sqrt(2)], actual value: " + y);
        }
        int borderHeight = (IMAGE_HEIGHT - GRAPHIC_HEIGHT) / 2;
        y = MAX_SIZE - y;
        return borderHeight + (int) (GRAPHIC_HEIGHT * y / MAX_SIZE);
    }

    private static void plotGrid(Graphics2D gr) {
        gr.setColor(Color.black);

        int gridx1 = (IMAGE_WIDTH - GRAPHIC_WIDTH) * 3 / 8;
        int gridx2 = (IMAGE_WIDTH - GRAPHIC_WIDTH) / 2 + GRAPHIC_WIDTH + (IMAGE_WIDTH - GRAPHIC_WIDTH) / 8;

        int gridy1 = (IMAGE_HEIGHT - GRAPHIC_HEIGHT) / 4;
        int gridy2 = (IMAGE_HEIGHT - GRAPHIC_HEIGHT) / 2 + GRAPHIC_HEIGHT + (IMAGE_HEIGHT - GRAPHIC_HEIGHT) / 4;

        gr.drawLine(gridx1, gridy1, gridx1, gridy2);
        gr.drawLine(gridx1, gridy2, gridx2, gridy2);

        for (float i = 0; i < MAX_SIZE + 0.01; i += 0.1) {
            gr.drawLine(xToPlot(i), gridy2 - 5, xToPlot(i), gridy2 + 5);
            gr.drawString(String.format("%.1f", i), xToPlot(i) - 5f, gridy2 + 25);
        }

        for (float i = 0; i < MAX_SIZE + 0.01; i += 0.1) {
            gr.drawLine(gridx1 - 5, yToPlot(i), gridx1 + 5, yToPlot(i));
            gr.drawString(String.format("%.1f", i), gridx1 - 30, yToPlot(i) + 5);
        }
    }

    private static void drawFacets(Graphics2D gr, List<Facet> facets, List<Vertex> vertices) {
        gr.setColor(Color.blue);

        for (Facet facet : facets) {
            int facetSize = facet.vertexIndices.size();
            int[] xPoints = new int[facetSize];
            int[] yPoints = new int[facetSize];
            for (int i = 0; i < facetSize; i++) {
                Integer vertexIndex = facet.vertexIndices.get(i);
                Vertex vertex = vertices.get(vertexIndex);
                xPoints[i] = xToPlot(vertex.getFloatX());
                yPoints[i] = yToPlot(vertex.getFloatY());
            }
            gr.drawPolygon(xPoints, yPoints, facetSize);
        }
    }

    private static void plotVertices(Graphics2D gr, List<Vertex> vertices) {
        gr.setColor(Color.black);
        gr.setFont(new Font("TimesRoman", Font.BOLD, 20));


        for (int i = 0; i < vertices.size(); i++) {
            List<Integer> verticesToPlot = new ArrayList<>();
            Vertex vertex = vertices.get(i);
            for (int j = 0; j < vertices.size(); j++) {
                if (vertices.get(j).equals(vertex)) verticesToPlot.add(j);
            }
            for (int k = 0; k < verticesToPlot.size(); k++) {
                gr.drawString(Integer.toString(verticesToPlot.get(k)), xToPlot(vertex.getFloatX()) + k * 20, yToPlot(vertex.getFloatY()));
            }
        }

        gr.setFont(new Font("TimesRoman", Font.PLAIN, 13));
    }

    private static void drawGraphics(BufferedImage image, List<Facet> facets, List<Vertex> vertices) {
        Graphics2D gr = image.createGraphics();

        gr.setPaint(Color.white);
        gr.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        drawFacets(gr, facets, vertices);
        plotGrid(gr);
        plotVertices(gr, vertices);
    }

    public static void visualizeSolution(Solution solution, String outputFileName) throws IOException {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH * 2, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gr = image.createGraphics();

        List<Facet> facets = solution.facets;

        BufferedImage image_source = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        List<Vertex> sourcePositions = solution.sourcePositions;
        drawGraphics(image_source, facets, sourcePositions);
        gr.drawImage(image_source, 0, 0, null);

        BufferedImage image_destination = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        List<Vertex> destinationPositions = solution.destinationPositions;
        drawGraphics(image_destination, facets, destinationPositions);
        gr.drawImage(image_destination, IMAGE_WIDTH, 0, null);

        ImageIO.write(image, "png", new File(outputFileName));
    }


}
