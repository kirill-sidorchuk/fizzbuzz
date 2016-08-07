package com.teamdev;

import com.teamdev.triangulation.PolygonHelper;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final String PROBLEMS_PATH = "problems\\initial";

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("problem id is expected in command line");
            return;
        }
        String problemID = args[0];
        Problem problem = ProblemReader.read(new File(PROBLEMS_PATH, problemID + ".txt"));
        ProblemVisualizer.visualizeProblem(problem, PROBLEMS_PATH + File.separator + problemID + ".png");

        Origami origami = problem.getOrigami();
        origami.findFacets();

        PolygonHelper polygonHelper = new PolygonHelper();
        polygonHelper.createBufferedImageFromVertices(problem.polygons, problemID);

        //    System.out.println(problem.toString());

    }
}
