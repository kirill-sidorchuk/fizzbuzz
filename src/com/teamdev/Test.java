package com.teamdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static final String TEST_FILE_PATH = "problems/our/g_4_utc4_rot_m90_spec.txt";
    private static final String TEST_PNG_PATH = "problems/our/g_spec.png";

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
        List<Vertex> sourcePositions = new ArrayList<>();
        sourcePositions.add(new Vertex(0, 1, 0, 1));
        sourcePositions.add(new Vertex(1, 1, 0, 1));
        sourcePositions.add(new Vertex(1, 1, 1, 1));
        sourcePositions.add(new Vertex(0, 1, 1, 1));
        sourcePositions.add(new Vertex(0, 1, 1, 2));
        sourcePositions.add(new Vertex(1, 2, 1, 2));
        sourcePositions.add(new Vertex(1, 2, 1, 1));

        List<Facet> facets = new ArrayList<>();
        ArrayList<Integer> indices1 = new ArrayList<Integer>() {{
            add(0);
            add(1);
            add(5);
            add(4);
        }};
        facets.add(new Facet(indices1));
        ArrayList<Integer> indices2 = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(6);
            add(5);
        }};
        facets.add(new Facet(indices2));
        ArrayList<Integer> indices3 = new ArrayList<Integer>() {{
            add(4);
            add(5);
            add(3);
        }};
        facets.add(new Facet(indices3));
        ArrayList<Integer> indices4 = new ArrayList<Integer>() {{
            add(5);
            add(6);
            add(3);
        }};
        facets.add(new Facet(indices4));

        List<Vertex> destinationPositions = new ArrayList<>();
        destinationPositions.add(new Vertex(0, 1, 0, 1));
        destinationPositions.add(new Vertex(1, 1, 0, 1));
        destinationPositions.add(new Vertex(0, 1, 0, 1));
        destinationPositions.add(new Vertex(0, 1, 0, 1));
        destinationPositions.add(new Vertex(0, 1, 1, 2));
        destinationPositions.add(new Vertex(1, 2, 1, 2));
        destinationPositions.add(new Vertex(0, 1, 1, 2));

        Solution solution = new Solution(sourcePositions, facets, destinationPositions);
        SolutionVisualizer.visualizeSolution(solution, SOLUTION_PNG_PATH);

        System.out.println("-----------------\n" + solution.toStringFormat());
    }

    public static void main(String[] args) throws IOException {
        problemReadingTest();

        problemVisualizationTest();
        solutionVisualizationTest();
    }
}
