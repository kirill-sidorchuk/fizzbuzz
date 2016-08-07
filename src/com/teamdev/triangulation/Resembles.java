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
        int[] areas = polygonHelper.createBufferedImageFromVertices(oPolygons, "myProblem");
           int allArea = 0;
        System.out.println("All area = " + areas[0]);
        for (int i = 1; i < areas.length; i++) {
            System.out.println("area [" + i + "] = " + areas[i]);
            allArea += areas[i];
        }

        int and = allArea - areas[0];
        int or = areas[0] - and;
        if (or == 0) return 0;
        System.out.println("or = " + or);
        System.out.println("and = " + and);
        System.out.println("Resembles = " + (double) and / (double) or);
        return (double) and / (double) or;


    }
}
