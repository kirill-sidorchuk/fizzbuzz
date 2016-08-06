package com.teamdev.fold;

import com.teamdev.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.teamdev.fold.PaperFolderHelper.getYForLine;

/**
 * @author Vladislav Kovchug
 */
public class PaperVisualizer {
    public static final int IMAGE_WIDTH = 1024;
    public static final int IMAGE_HEIGHT = 768;
    public static final int SCALE = 600;
    public static final int PADDING = 10;

    private void drawPaper(BufferedImage image, Paper paper, FoldLine foldLine) {
        Graphics2D gr = image.createGraphics();

        gr.setPaint(Color.white);
        gr.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        final List<Vertex> vertices = paper.getPolygons().get(0).vertices;
        int pointsCount = vertices.size();
        int[] xPoints = new int[pointsCount];
        int[] yPoints = new int[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            xPoints[i] = transformXCoordinate(vertices.get(i).getFloatX());
            yPoints[i] = transformYCoordinate(vertices.get(i).getFloatY());

        }

        gr.setColor(Color.pink);
        gr.fillPolygon(xPoints, yPoints, pointsCount);

        if (foldLine != null) {
            gr.setColor(Color.red);
            gr.setStroke(new BasicStroke(3));

            final float x1 = 0;
            final float x2 = 2;

            gr.drawLine(transformXCoordinate(x1), transformYCoordinate(getYForLine(foldLine, x1)),
                    transformXCoordinate(x2), transformYCoordinate(getYForLine(foldLine, x2)));
        }

    }


    public void draw(Paper paper, File file) {
        draw(paper, null, file);
    }

    public void draw(Paper paper, FoldLine foldLine, File file) {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

        drawPaper(image, paper, foldLine);

        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int transformXCoordinate(double value) {
        return (int) (value * SCALE) + PADDING;
    }

    private int transformYCoordinate(double value) {
        return IMAGE_HEIGHT - ((int) (value * SCALE) + PADDING);
    }


}
