package com.teamdev.problem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Kovchug
 */
public class ProblemGenerator {

    public static void main(String[] args) {
        int n = 4;
        final int pow = (int) Math.pow(2, n)+1;
        System.out.println(pow*3);

        List<Integer> bottomLine = new ArrayList<>(pow + 1);
        List<Integer> middleLine = new ArrayList<>(pow + 1);
        List<Integer> topLine = new ArrayList<>(pow + 1);



        for (int i = 0; i < pow; i++) {
            System.out.println(i+"/" + (pow - 1) + ",0");
        }
        System.out.println("---");
        for (int i = 0; i < pow; i++) {
            if(i%2 != 0){
                System.out.println(i+"/" + (pow - 1) + ",1/2");
            } else {
                System.out.println(i+"/" + (pow - 1) + "," + (((pow - 1) / 2) + 1) + "/" + (pow - 1));
            }
        }
        System.out.println("---");
        for (int i = 0; i < pow; i++) {
            System.out.println(i+"/" + (pow - 1) + ",1");
        }

        System.out.println("---");
        System.out.println(2*(pow-1));
        for(int i=0; i<pow-1; i++){
            System.out.println("4 " + i + " " + (i+1) + " " + (i+1+pow) + " " + (i+pow));
        }
        System.out.println("---");
        for(int i=pow; i<2*(pow)-1; i++){

            System.out.println("4 " + i + " " + (i+1) + " " + (i+1+pow) + " " + (i+pow));
        }

        System.out.println("---");
        System.out.println("---");

        for (int i = 0; i < pow; i++) {
            if(i%2 ==0){
                System.out.println("0,0");
            } else {
                System.out.println(1+"/" + (pow - 1) + ",0");
            }

        }

        System.out.println("---");

        for (int i = 0; i < pow; i++) {
            if(i%2 != 0){
                System.out.println(1+"/" + (pow - 1) + ",1/2");
            } else {
                System.out.println(0+"/" + (pow - 1) + "," + (((pow - 1) / 2) + 1) + "/" + (pow - 1));
            }
        }
        System.out.println("---");
        for (int i = 0; i < pow; i++) {
            String y = "";
            if(i%2 != 0){
                y = "1/2";
            } else {
                y = (((pow - 1) / 2) + 1) + "/" + (pow - 1);
            }

            System.out.println("-" + (((pow - 1) / 2) - 1) + "/" + (pow - 1) + "," + y);
        }

    }

}
