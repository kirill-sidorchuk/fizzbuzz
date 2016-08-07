package com.teamdev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class ImperfectSolver {

    public static Solution getSolution(Problem problem) {
        // blind square solver
        // return blindSquareSolution();

        if( problem.polygons.size() == 1 && problem.polygons.get(0).calcArea().equals(new Fraction(1,1))) {
            // solving perfectly any shifted and rotated square
            return shiftedSqaureSolution(problem);
        }
        else {
            // shifted square
            return centeredSquareSolution(problem);
        }
    }

    private static Solution shiftedSqaureSolution(Problem problem) {
        List<Vertex> v = new ArrayList<>();
        v.add(new Vertex(new Fraction(0), new Fraction(0)));
        v.add(new Vertex(new Fraction(1), new Fraction(0)));
        v.add(new Vertex(new Fraction(1), new Fraction(1)));
        v.add(new Vertex(new Fraction(0), new Fraction(1)));

        List<Facet> facets = new ArrayList<>();
        List<Integer> fIndexes = new ArrayList<>();
        fIndexes.add(0);
        fIndexes.add(1);
        fIndexes.add(2);
        fIndexes.add(3);
        facets.add(new Facet(fIndexes));

        return new Solution(v, facets, problem.polygons.get(0).vertices);
    }

    private static Solution blindSquareSolution() {
        return Solution.getBlindSquare();
    }

    private static Solution centeredSquareSolution(Problem problem) {
        List<Vertex> v = new ArrayList<>();
        v.add(new Vertex(new Fraction(0), new Fraction(0)));
        v.add(new Vertex(new Fraction(1), new Fraction(0)));
        v.add(new Vertex(new Fraction(1), new Fraction(1)));
        v.add(new Vertex(new Fraction(0), new Fraction(1)));
        OPolygon polygon = new OPolygon(v);
        polygon.moveToCenter(problem.getCenter());

        List<Facet> facets = new ArrayList<>();
        List<Integer> fIndexes = new ArrayList<>();
        fIndexes.add(0);
        fIndexes.add(1);
        fIndexes.add(2);
        fIndexes.add(3);
        facets.add(new Facet(fIndexes));
        return new Solution(v, facets, polygon.vertices);
    }

    public static File[] listProblems(File dir) {
        return dir.listFiles((dir1, name) -> name.endsWith(".txt") && !name.contains("_solution") && !name.contains("_result"));
    }

    public static void main(String[] args) {
        File srcDir = new File(args[0]);

        File[] problemFiles = listProblems(srcDir);

        for (File problemFile : problemFiles) {
            try {
                System.out.println("reading " + problemFile.getName());
                File solutionFile = Utils.getSolutionFile(problemFile);
                if( Utils.isPerfetlySolved(solutionFile)) continue;

                Problem problem = ProblemReader.read(problemFile);

                Solution solution = getSolution(problem);
                solution.save(solutionFile);

            } catch (IOException e) {
                System.out.println("Failed to process: " + problemFile.getPath());
                e.printStackTrace();
            }
        }

    }

}
