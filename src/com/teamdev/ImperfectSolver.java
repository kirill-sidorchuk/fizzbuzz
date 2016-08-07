package com.teamdev;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class ImperfectSolver {

    public static Solution getSolution(Problem problem) {
        // blind square solver
        // return blindSquareSolution();

        if( problem.calcArea().equals(new Fraction(1,1))) {
            // solving perfectly any shifted and rotated square
            Solution solution = shiftedSqaureSolution(problem);
            solution.isPerfect = true;
            return solution;
        }
        else {

            // folding square to get similar area
//            return foldedSquareSolution(problem);

            // shifted square
            return centeredSquareSolution(problem);
        }
    }

    private static Solution foldedSquareSolution(Problem problem) {
        Fraction area = problem.calcArea();

        int nFolds = (int) Math.round(Math.log(new Fraction(1).div(area).getDoubleValue())/Math.log(2));

        if( nFolds < 1 ) {
            return centeredSquareSolution(problem);
        }
        else {

            Origami origami = Origami.getSquare();

            Vertex xBase = new Vertex(new Fraction(1,2), new Fraction(0));
            Vertex yBase = new Vertex(new Fraction(0), new Fraction(1,2));
            Vertex xDir = new Vertex(new Fraction(0), new Fraction(1));
            Vertex yDir = new Vertex(new Fraction(-1), new Fraction(0));

            for( int f=0; f<nFolds; ++f) {

                Vertex base = null;
                Vertex dir = null;
                if( 0 == (f % 2)) {
                    base = xBase;
                    dir = xDir;
                }
                else {
                    base = yBase;
                    dir = yDir;
                }

                FoldVector foldVector = new FoldVector(base, dir);

                Origami[] origamis = origami.fold(foldVector);

                base.x.d = base.x.d.multiply(BigInteger.valueOf(2));
                base.y.d = base.y.d.multiply(BigInteger.valueOf(2));
                base.normalize();
            }

            System.out.println(nFolds);
        }

        return null;
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

        int nDeletedFiles = 0;
        int nPerfectSolutions = 0;

        for (File problemFile : problemFiles) {
            try {
                File solutionFile = Utils.getSolutionFile(problemFile);
                if( Utils.isPerfetlySolved(solutionFile)) continue;

                System.out.println("solving " + problemFile.getName());
                Problem problem = null;
                try {
                    problem = ProblemReader.read(problemFile);
                } catch (Exception e) {
                    e.printStackTrace();

                    System.out.println("deleting " + problemFile.getPath());
                    if( !problemFile.delete() ) {
                        System.out.println("couldn't delete " + problemFile.getPath());
                    }
                    else {
                        nDeletedFiles++;
                    }

                    continue;
                }

                Solution solution = getSolution(problem);
                if( solution.isPerfect )
                    nPerfectSolutions++;
                solution.save(solutionFile);

            } catch (Exception e) {
                System.out.println("Failed to process: " + problemFile.getPath());
                e.printStackTrace();
            }
        }

        System.out.println("count of deleted files = " + nDeletedFiles);
        System.out.println("count of perfect solutions = " + nPerfectSolutions);

    }

}
