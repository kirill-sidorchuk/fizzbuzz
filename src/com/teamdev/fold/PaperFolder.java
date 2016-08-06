package com.teamdev.fold;


import com.teamdev.OPolygon;
import com.teamdev.Vertex;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.fold.PaperFolderHelper.getYForLine;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolder {

    public Paper fold(Paper paper, FoldLine line){
        final Paper result = new Paper(new ArrayList<>());

        for (OPolygon polygon : paper.getPolygons()) {
            final List<Vertex> leftPolygon = new ArrayList<>();
            final List<Vertex> rightPolygon = new ArrayList<>();

            //select bottom and top point of line
            //there call sergey API
            //Vertex bottomPoint = line.getV1().getFloatY() <= line.getV2().getFloatY() ? line.getV1() : line.getV2();
            //Vertex topPoint = line.getV1().getFloatY() > line.getV2().getFloatY() ? line.getV1() : line.getV2();
            Vertex bottomPoint = new Vertex();
            Vertex topPoint = new Vertex();


            rightPolygon.add(bottomPoint);
            leftPolygon.add(topPoint);
            for (Vertex v : polygon.vertices) {
                if(cmpWithLine(line, v) < 0){ // if on the right side, push to
                    rightPolygon.add(v);
                } else if(cmpWithLine(line, v) > 0){
                    leftPolygon.add(v);
                }
            }

            rightPolygon.add(topPoint);
            leftPolygon.add(bottomPoint);
            result.add(new OPolygon(rightPolygon));
            result.add(new OPolygon(revertPolygon(leftPolygon, line)));
        }

        return result;
    }

    public static double cmpWithLine(FoldLine line, Vertex p){
        final double yOnLine = getYForLine(line, p.getFloatX());
        return Double.compare(yOnLine, p.getFloatY());
    }

    private List<Vertex> revertPolygon(List<Vertex> polygon, FoldLine line){
        final ArrayList<Vertex> result = new ArrayList<Vertex>(polygon.size());

        return result;
    }

}
