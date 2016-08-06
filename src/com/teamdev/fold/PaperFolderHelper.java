package com.teamdev.fold;

import com.teamdev.Fraction;
import com.teamdev.OPolygon;
import com.teamdev.Vertex;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolderHelper {

    public FoldLine investigateFoldLine(){
        //return new FoldLine(new Vertex(0, 1, 0, 1), new Vertex(1, 1, 1, 1));

        final Random random = new Random();

        final Vertex v1 = new Vertex(random.nextInt(32), 32, random.nextInt(32), 32);
        final Vertex v2 = new Vertex(random.nextInt(32), 32, random.nextInt(32), 32);

        //final Vertex v1 = new Vertex(1, 32, 28, 32);
        //final Vertex v2 = new Vertex(22, 32, 16, 32);

        System.out.println("p1: (" + v1 + ") p2:(" + v2 + ") FLOAT: " +
                "( " + v1.getFloatX() + "," + v1.getFloatY() + " ) " +
                "( " + v2.getFloatX() + "," + v2.getFloatY() + " ) ");
        final FoldLine foldLine = new FoldLine(v1, v2);
        System.out.println("line: k=" + foldLine.getFractionK() + " c=" + foldLine.getFractionC());
        return foldLine;
    }

    public static Paper createInitialPaper(){
        final ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(0,1, 0, 1));
        vertices.add(new Vertex(1, 1, 0, 1));
        vertices.add(new Vertex(1, 1, 1, 1));
        vertices.add(new Vertex(0, 1, 1, 1));
        final ArrayList<OPolygon> oPolygons = new ArrayList<>();
        oPolygons.add(new OPolygon(vertices));
        return new Paper(oPolygons);
    }

    public static double getYForLine(FoldLine line, double x){
        return line.getK()*x + line.getC();
    }

    public static Fraction getYForLine(FoldLine line, Fraction x){
        return line.getFractionK().mul(x).add(line.getFractionC());
    }

}
