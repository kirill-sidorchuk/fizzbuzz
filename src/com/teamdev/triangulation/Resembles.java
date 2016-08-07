package com.teamdev.triangulation;

import com.teamdev.OPolygon;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Sergey on 07.08.2016.
 */
public class Resembles {

    public double getResembles(List<OPolygon> oPolygons) {

        PolygonHelper polygonHelper = new PolygonHelper();
        List<BufferedImage> bufferedImages = polygonHelper.createBufferedImageFromVertices(oPolygons, "myProblem");
        int[] areas = new int[bufferedImages.size()];
        for (int i = 0; i < bufferedImages.size(); i++) {
            areas[i] = polygonHelper.getPolygonPixelArea(bufferedImages.get(i));
        }
        int allArea = 0;
        for (int i = 1; i < bufferedImages.size(); i++) {
            allArea += areas[i];
        }
        int and = allArea - areas[0];
        int or = areas[0] - and;
        if (or == 0) return 0;

        return (double) and / (double) or;


    }
}
