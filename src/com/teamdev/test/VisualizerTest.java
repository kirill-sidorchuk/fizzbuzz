package com.teamdev.test;

import com.teamdev.Problem;
import com.teamdev.ProblemReader;
import com.teamdev.ProblemVisualizer;

import java.io.File;
import java.io.IOException;

/**
 * @author Vladislav Kovchug
 */
public class VisualizerTest {

    private static final String TEST_FILE_PATH = "problems/initial";
    public static final int PROBLEM_ID = 2;

    public static void main(String[] args) {
        Integer problemID = PROBLEM_ID;


        try {
            Problem problem = ProblemReader.read(new File(TEST_FILE_PATH, problemID + ".txt"));
            ProblemVisualizer.visualizeProblem(problem, problemID + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
