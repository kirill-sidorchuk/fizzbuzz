package com.teamdev;

import com.teamdev.triangulation.Resembles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class ImperfectSolver {

    public static final String PROBLEMS_PERFECT_LIST = "problems/perfect.list";

    public static Solution getSolution(Problem problem, List<HandSolution> handCodedProblems) {
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
//            return centeredSquareSolution(problem);

            // resebmles-based best search
            //return handcodedSquareFoldsSolutionWithResembles(problem, handCodedProblems);

            return handcodedSquareFoldsSolution(problem, handCodedProblems);
        }
    }

    private static Solution handcodedSquareFoldsSolution(Problem problem, List<HandSolution> handCodedProblems) {

        Fraction minDiff = null;
        HandSolution bestSolution = null;

        Fraction probArea = problem.calcArea();

        for (HandSolution handSolution : handCodedProblems) {

            Fraction handArea = handSolution.polygon.calcArea();
            Fraction diff = handArea.sub(probArea).abs();
            if( minDiff == null || diff.compareTo(minDiff) < 0 ) {
                minDiff = diff;
                bestSolution = handSolution;
            }
        }

        Vertex neededCenter = problem.getCenter();
        Vertex T = neededCenter.sub(bestSolution.polygon.getCenter());
        Solution translated = bestSolution.solution.translate(T);

        return translated;
    }

    private static Solution handcodedSquareFoldsSolutionWithResembles(Problem problem, List<HandSolution> handCodedProblems) {

        double maxResembles = 0;
        Solution bestSolution = null;

        for (HandSolution handSolution : handCodedProblems) {

            // best translation
            Vertex neededCenter = problem.getCenter();
            OPolygon translatedPoly = handSolution.polygon.moveToCenterCopy(neededCenter);

            List<OPolygon> polys = new ArrayList<>();
            polys.add(translatedPoly);
            polys.add(problem.polygons.get(0));

            double resembles = Resembles.getResembles(polys);

            if( bestSolution == null || resembles > maxResembles) {
                maxResembles = resembles;
                Vertex T = neededCenter.sub(handSolution.polygon.getCenter());
                bestSolution = handSolution.solution.translate(T);
            }
        }

        System.out.println("max resembles = " + maxResembles);

        return bestSolution;
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
        int nNewSolutions = 0;

        StringBuilder perfectSB = new StringBuilder();

        // loading hand-coded solutions
        List<HandSolution> handCodedSolutions = loadHandCodedSolutions();

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

                if( problemFile.getName().equals("1094.txt")) {
                    System.out.println("s");
                }

                Solution solution = getSolution(problem, handCodedSolutions);
                if( solution.isPerfect ) {
                    nPerfectSolutions++;
                    perfectSB.append(problemFile.getName()).append("\n");
                }
                solution.save(solutionFile);
                nNewSolutions ++;

            } catch (Exception e) {
                System.out.println("Failed to process: " + problemFile.getPath());
                e.printStackTrace();
            }
        }

        System.out.println("count of deleted files = " + nDeletedFiles);
        System.out.println("count of perfect solutions = " + nPerfectSolutions);
        System.out.println("count of new solutions = " + nNewSolutions);

        try {
            Utils.writeStringToFile(new File(PROBLEMS_PERFECT_LIST), perfectSB.toString());
        } catch (FileNotFoundException e) {
            System.out.println("failed to save list of perfect solutions");
            e.printStackTrace();
        }

    }

    private static class HandSolution {
        public Solution solution;
        public OPolygon polygon;

        public HandSolution(Solution solution, OPolygon polygon) {
            this.solution = solution;
            this.polygon = polygon;
        }
    }

    private static List<HandSolution> loadHandCodedSolutions() {
        List<HandSolution> handCodedSolutions = new ArrayList<>();
        try {
            List<Vertex> v1 = new ArrayList<>();
            v1.add(new Vertex(new Fraction(0), new Fraction(0)));
            v1.add(new Vertex(new Fraction(1,2), new Fraction(0)));
            v1.add(new Vertex(new Fraction(1,2), new Fraction(1)));
            v1.add(new Vertex(new Fraction(0), new Fraction(1)));
            handCodedSolutions.add(new HandSolution(SolutionParser.parse(new File("problems/FoldOneSquarSolution.txt")), new OPolygon(v1)));

            List<Vertex> v2 = new ArrayList<>();
            v2.add(new Vertex(new Fraction(0), new Fraction(0)));
            v2.add(new Vertex(new Fraction(1,2), new Fraction(0)));
            v2.add(new Vertex(new Fraction(1,2), new Fraction(1,2)));
            v2.add(new Vertex(new Fraction(0), new Fraction(1,2)));
            handCodedSolutions.add(new HandSolution(SolutionParser.parse(new File("problems/TwoFoldsSquareSolution.txt")), new OPolygon(v2)));

            List<Vertex> v3 = new ArrayList<>();
            v3.add(new Vertex(new Fraction(0), new Fraction(0)));
            v3.add(new Vertex(new Fraction(1,2), new Fraction(0)));
            v3.add(new Vertex(new Fraction(1,2), new Fraction(1,4)));
            v3.add(new Vertex(new Fraction(0), new Fraction(1,4)));
            handCodedSolutions.add(new HandSolution(SolutionParser.parse(new File("problems/TreeFoldsSquareSolution.txt")), new OPolygon(v3)));
        } catch (IOException e) {
            System.out.println("Failed to load hand coded problems");
            e.printStackTrace();
        }
        return handCodedSolutions;
    }

}
