package com.teamdev;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class ProblemSubmitter {

    public static final int REQUEST_SLEEP_MS = 1500;

    public static void main(String[] args) throws IOException {
        File srcDir = new File(args[0]);

        if( srcDir.isDirectory() )
            submitSolutions(srcDir);
        else
            submitBlindSquares(srcDir);
    }

    private static void submitBlindSquares(File idsFile) throws IOException {
        List<String> ids = Utils.readLines(idsFile);
        Set<String> idsSet = new HashSet<>(ids);

        System.out.println("ids to blind submit = " + idsSet.size());

        int countOfPerfectlySolved = 0;

        File blindSquareSolution = new File("problems/initial/1_solution.txt");
        for (String id : idsSet) {

            File solutionFile = new File("problems/initial/" + id + "_solution.txt");
            if( solutionFile.exists()) {
                System.out.println("skipping: " +  solutionFile.getName());
                continue;
            }

            System.out.println("submitting: " +  solutionFile.getName());

            String resemblanceString = submit(blindSquareSolution, id);
            if( resemblanceString == null ) {
                System.out.println("Submit failed for: " + id);
            }
            else {
                System.out.println("resemblance = " + resemblanceString);
                if( resemblanceString.equals("1.0")) {
                    countOfPerfectlySolved++;
                    System.out.println("countOfPerfectlySolved = " + countOfPerfectlySolved);
                }
                Utils.writeStringToFile(Utils.getResultFile(solutionFile), resemblanceString);
            }

            try {
                Thread.sleep(REQUEST_SLEEP_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("countOfPerfectlySolved = " + countOfPerfectlySolved);
    }

    private static void submitSolutions(File srcDir) {
        File[] solutionFiles = listSolutions(srcDir);

        for (File solutionFile : solutionFiles) {
            try {

                boolean isPerfetlySolved = Utils.isPerfetlySolved(solutionFile);
                boolean isSolved = Utils.isSolved(solutionFile);

                if( isPerfetlySolved && isSolved ) continue;

                System.out.println("\nSubmitting: " + solutionFile.getName());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String resemblanceString = submit(solutionFile);
                if( resemblanceString == null ) {
                    System.out.println("Submit failed for: " + solutionFile.getName());
                }
                else {
                    System.out.println("resemblance = " + resemblanceString);
                    Utils.writeStringToFile(Utils.getResultFile(solutionFile), resemblanceString);

//                    if( resemblanceString.equals("1.0")) {
//                        System.out.println("Solved PERFECTLY: " + solutionFile);
//                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to submit: " + solutionFile.getPath());
                e.printStackTrace();
            }
        }

    }

    private static String submit(File solutionFile) throws IOException {
        String id = solutionFile.getName().replace("_solution.txt", "");
        return submit(solutionFile, id);
    }

    private static String submit(File solutionFile, String id) throws IOException {
        List<String> args = new ArrayList<>();
        args.add("--compressed");
        args.add("-L");
        args.add("-H");
        args.add("Expect:");
        args.add("-H");
        args.add("\"X-API-Key: 175-e9b9b81095330bbeed88bef333785d74\"");
        args.add("-F");
        args.add("\"problem_id=" + id + "\"");
        args.add("-F");
        args.add("\"solution_spec=@" + solutionFile.getPath().replace("\\", "/") + "\"");
        args.add("\"http://2016sv.icfpcontest.org/api/solution/submit\"");
        return runCurl(args);
    }

    private static String runCurl(List<String> args) throws IOException {
        List<String> command = new ArrayList<>();
        command.add("curl.exe");
        command.addAll(args);
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        String resultString = null;

        while ((line = br.readLine()) != null) {
            String resemblString = "resemblance\":";
            int i = line.indexOf(resemblString);
            if( i != -1 ) {
                i += resemblString.length();
                int j = line.indexOf(",", i);
                if( j != -1 ) {
                    resultString = line.substring(i, j);
                }
            }
            System.out.println(line);
        }

        return resultString;
    }

    private static File[] listSolutions(File srcDir) {
        File[] txtList = srcDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".txt") && name.contains("_solution");
            }
        });
        return txtList;
    }
}
