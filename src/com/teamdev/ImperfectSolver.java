package com.teamdev;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class ImperfectSolver {

    public static Solution getSolution(Problem problem) {
        // blind square solver
        return blindSquareSolution();
    }

    private static Solution blindSquareSolution() {
        return Solution.getBlindSquare();
    }

    public static File[] listProblems(File dir) {
        return dir.listFiles((dir1, name) -> name.endsWith(".txt") && !name.contains("_solution"));
    }

    public static void main(String[] args) {
        File srcDir = new File(args[0]);

        File[] problemFiles = listProblems(srcDir);

        for (File problemFile : problemFiles) {
            try {
                System.out.println("reading " + problemFile.getName());
                Problem problem = ProblemReader.read(problemFile);

                Solution solution = getSolution(problem);
                File solutionFile = new File(problemFile.getPath().replace(".txt", "_solution.txt"));
                solution.save(solutionFile);

            } catch (IOException e) {
                System.out.println("Failed to process: " + problemFile.getPath());
                e.printStackTrace();
            }
        }

    }
}
