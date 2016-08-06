package com.teamdev.fold;

import com.teamdev.Fraction;
import com.teamdev.OPolygon;
import com.teamdev.Vertex;

import java.util.ArrayList;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolderHelper {

    public FoldLine investigateFoldLine(){
        return new FoldLine(new Vertex(0, 1, 1, 2), new Vertex(1, 1, 1, 2));
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
