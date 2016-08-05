package com.teamdev.fold;


import com.teamdev.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolder {

    public Paper fold(Paper paper, FoldLine line){
        final Paper result = new Paper(new ArrayList<>());
        for (List<Vertex> vertices : paper.getVertices()) {
            final List<Vertex> leftPolygon = new ArrayList<>();
            final List<Vertex> rightPolygon = new ArrayList<>();

            //select bottom and top point of line
            Vertex bottomPoint = line.getV1().getFloatY() <= line.getV2().getFloatY() ? line.getV1() : line.getV2();
            Vertex topPoint = line.getV1().getFloatY() > line.getV2().getFloatY() ? line.getV1() : line.getV2();

            rightPolygon.add(bottomPoint);
            leftPolygon.add(topPoint);
            for (Vertex v : vertices) {
                if(cmpWithLine(line, v) < 0){ // if on the right side, push to
                    rightPolygon.add(v);
                } else if(cmpWithLine(line, v) > 0){
                    leftPolygon.add(v);
                }
            }

            rightPolygon.add(topPoint);
            leftPolygon.add(bottomPoint);
            result.add(rightPolygon);
            result.add(revertPolygon(leftPolygon, line));
        }

        return result;
    }

    public static double cmpWithLine(FoldLine line, Vertex p){
        final Vertex v1 = line.getV1().getFloatY() <= line.getV2().getFloatY() ? line.getV1() : line.getV2();
        final Vertex v2 = line.getV1().getFloatY() > line.getV2().getFloatY() ? line.getV1() : line.getV2();

        return ((v2.getFloatX() - v1.getFloatX())*(p.getFloatY() - v1.getFloatY()) - (v2.getFloatY() - v1.getFloatY())*(p.getFloatX() - v1.getFloatX()));
    }

    private List<Vertex> revertPolygon(List<Vertex> polygon, FoldLine line){
        final ArrayList<Vertex> result = new ArrayList<Vertex>(polygon.size());

        return result;
    }

}
