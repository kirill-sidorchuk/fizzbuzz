package com.teamdev;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class ProblemVisualizer extends Frame {
    public static final int IMAGE_WIDTH = 1024;
    public static final int IMAGE_HEIGHT = 768;

    public static final int GRAPHIC_WIDTH = 600;
    public static final int GRAPHIC_HEIGHT = 600;

    public static final float MAX_SIZE = (float) Math.sqrt(2);

    public static int xToPlot(float x) {
        if (x < 0 || x > MAX_SIZE) {
            throw new IllegalArgumentException("value of x expected to be in range [0:sqrt(2)], actual value: " + x);
        }
        int borderWidth = (IMAGE_WIDTH - GRAPHIC_WIDTH) / 2;
        return borderWidth + (int) (GRAPHIC_WIDTH * x / MAX_SIZE);
    }

    public static int[] xToPlot(float[] xs) {
        int[] ixs = new int[xs.length];
        for(int i=0; i<xs.length; ++i)
            ixs[i] = xToPlot(xs[i]);
        return ixs;
    }

    public static int[] yToPlot(float[] ys) {
        int[] iys = new int[ys.length];
        for(int i=0; i<ys.length; ++i)
            iys[i] = yToPlot(ys[i]);
        return iys;
    }

    public static int yToPlot(float y) {
        if (y < 0 || y > MAX_SIZE) {
            throw new IllegalArgumentException("value of y expected to be in range [0:sqrt(2)], actual value: " + y);
        }
        int borderHeight = (IMAGE_HEIGHT - GRAPHIC_HEIGHT) / 2;
        y = MAX_SIZE - y;
        return borderHeight + (int) (GRAPHIC_HEIGHT * y / MAX_SIZE);
    }

    private static void plotPolygons(Graphics2D gr, Problem problem) {
        for (OPolygon polygon : problem.polygons) {
            gr.setColor(Color.black);

            int pointsCount = polygon.vertices.size();
            int[] xPoints = new int[pointsCount];
            int[] yPoints = new int[pointsCount];

            for (int i = 0; i < pointsCount; i++) {
                float x = polygon.vertices.get(i).getFloatX();
                xPoints[i] = xToPlot(x);

                float y = polygon.vertices.get(i).getFloatY();
                yPoints[i] = yToPlot(y);
            }

            gr.setColor(Color.pink);
            gr.fillPolygon(xPoints, yPoints, pointsCount);
        }
    }

    private static void plotLines(Graphics2D gr, Problem problem) {
        gr.setColor(Color.red);
        gr.setStroke(new BasicStroke(3));

        for (LineSegment lineSegment : problem.lineSegments) {
            gr.drawLine(xToPlot(lineSegment.v1.getFloatX()), yToPlot(lineSegment.v1.getFloatY()),
                    xToPlot(lineSegment.v2.getFloatX()), yToPlot(lineSegment.v2.getFloatY()));
        }
    }

    private static void plotLines(Graphics2D gr, Origami origami) {
        gr.setColor(Color.red);
        gr.setStroke(new BasicStroke(3));

        for (Edge edge : origami.edges) {
            Vertex v1 = origami.vertices.get(edge.i0);
            Vertex v2 = origami.vertices.get(edge.i1);
            gr.drawLine(xToPlot(v1.getFloatX()), yToPlot(v1.getFloatY()),
                    xToPlot(v2.getFloatX()), yToPlot(v2.getFloatY()));
        }
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

    private static void plotVertices(Graphics2D gr, Problem problem) {
        gr.setFont(new Font("TimesRoman", Font.BOLD, 20));

        for (OPolygon polygon : problem.polygons) {
            for (int i = 0; i < polygon.vertices.size(); i++) {
                Vertex vertex = polygon.vertices.get(i);
                gr.drawString(Integer.toString(i), xToPlot(vertex.getFloatX()), yToPlot(vertex.getFloatY()));
            }
        }

        gr.setFont(new Font("TimesRoman", Font.PLAIN, 13));
    }

    private static void plotVertices(Graphics2D gr, Origami origami) {
        gr.setFont(new Font("TimesRoman", Font.BOLD, 20));

        for (int i = 0; i < origami.vertices.size(); i++) {
                Vertex vertex = origami.vertices.get(i);
                gr.drawString(Integer.toString(i), xToPlot(vertex.getFloatX()), yToPlot(vertex.getFloatY()));
        }

        gr.setFont(new Font("TimesRoman", Font.PLAIN, 13));
    }

    private static void drawGraphics(BufferedImage image, Problem problem) {
        Graphics2D gr = image.createGraphics();

        gr.setPaint(Color.white);
        gr.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        plotPolygons(gr, problem);

        plotLines(gr, problem);

        plotGrid(gr);

        plotVertices(gr, problem);
    }

    private static void drawGraphics(BufferedImage image, Origami origami) {
        Graphics2D gr = image.createGraphics();

        gr.setPaint(Color.white);
        gr.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        plotFacets(gr, origami);

        plotLines(gr, origami);

        plotGrid(gr);

        plotVertices(gr, origami);
    }

    private static void plotFacets(Graphics2D gr, Origami origami) {
        for (Facet facet : origami.facets) {
            gr.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));

            float[] fxs = facet.getXs(origami.vertices);
            float[] fys = facet.getYs(origami.vertices);

            gr.fillPolygon(xToPlot(fxs),yToPlot(fys), fxs.length);
        }

    }

    public static void visualizeProblem(Problem problem, String outputFileName) throws IOException {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

        drawGraphics(image, problem);

        ImageIO.write(image, "png", new File(outputFileName));
    }


    public static void visualizeOrigami(Origami origami, String outputFileName) throws IOException {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        drawGraphics(image, origami);
        ImageIO.write(image, "png", new File(outputFileName));
    }

    public static void visualizeFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        File[] txtList = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".txt") && !name.contains("_solution") && !name.contains("_result");
            }
        });
        for (File file: txtList) {
            visualizeOrigamiFile(file);
        }
    }

    private static void visualizeOrigamiFile(File file) throws IOException {
        try {
            System.out.println(file.getName());
            Origami origami = ProblemReader.read(file).getOrigami();
            System.out.println("searching for facets");
            origami.findFacets();
            visualizeOrigami(origami, file.getAbsolutePath().replace(".txt", ".png"));
        } catch (RuntimeException e) {
            System.out.println("Failed to visualize: " + file.getName());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        visualizeOrigamiFile(new File("problems/initial/15.txt"));
        visualizeFolder(args[0]);
    }
}