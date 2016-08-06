package com.teamdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static final String TEST_FILE_PATH = "problems/initial/9.txt";
    private static final String TEST_PNG_PATH = "problems/initial/9.png";

    private static final String SOLUTION_TEST_PATH = "solutions/initial/test.txt";
    private static final String SOLUTION_PNG_PATH = "solutions/initial/test.png";

    private static void problemReadingTest() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        Problem problem = ProblemReader.read(testFile);

        System.out.println("Polygons:");
        for (OPolygon p : problem.polygons) {
            System.out.println(p.toString());
        }

        System.out.println("Line segments:");
        for (LineSegment l : problem.lineSegments) {
            System.out.println(l.toString());
        }
    }

    private static void problemVisualizationTest() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        Problem problem = ProblemReader.read(testFile);
        ProblemVisualizer.visualizeProblem(problem, TEST_PNG_PATH);
        System.out.println("--------------------------------");
        System.out.println(problem.toStringFormat());
    }

    private static void solutionVisualizationTest() throws IOException {
        SolutionParser solutionParser = new SolutionParser();
        Solution solution = solutionParser.parse(new File(SOLUTION_TEST_PATH));

        SolutionVisualizer.visualizeSolution(solution, SOLUTION_PNG_PATH);

        System.out.println("-----------------\n" + solution.toStringFormat());
    }

    private static void origamiVisualizationTest() throws IOException {
        Problem problem = ProblemReader.read(new File(TEST_FILE_PATH));
        Origami origami = problem.getOrigami();
        ProblemVisualizer.visualizeOrigami(origami, TEST_PNG_PATH);
    }

    public static void main(String[] args) throws IOException {
        problemReadingTest();

        problemVisualizationTest();
        solutionVisualizationTest();
        origamiVisualizationTest();
    }
}
