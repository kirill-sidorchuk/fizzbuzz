package com.teamdev.fold;


import com.teamdev.Fraction;
import com.teamdev.OPolygon;
import com.teamdev.Vertex;
import com.teamdev.triangulation.LineHelper;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.fold.PaperFolderHelper.getYForLine;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolder {

    public Paper fold(Paper paper, FoldLine line){
        final LineHelper lineHelper = new LineHelper();
        final Paper result = new Paper(new ArrayList<>());

        for (OPolygon polygon : paper.getPolygons()) {
            final List<Vertex> intersectionPoints = lineHelper.getIntersections(polygon, line);
            if(intersectionPoints.size() < 2){
                result.add(new OPolygon(revertPolygon(polygon.vertices, line)));
                continue;
            }

            final List<Vertex> leftPolygon = new ArrayList<>();
            final List<Vertex> rightPolygon = new ArrayList<>();

            //select bottom and top point of line
            //there call sergey API
            //Vertex bottomPoint = line.getV1().getFloatY() <= line.getV2().getFloatY() ? line.getV1() : line.getV2();
            //Vertex topPoint = line.getV1().getFloatY() > line.getV2().getFloatY() ? line.getV1() : line.getV2();
            Vertex bottomPoint = intersectionPoints.get(0).getFloatY() <= intersectionPoints.get(1).getFloatY() ? intersectionPoints.get(0) : intersectionPoints.get(1);
            Vertex topPoint = intersectionPoints.get(0).getFloatY() > intersectionPoints.get(1).getFloatY() ? intersectionPoints.get(0) : intersectionPoints.get(1);

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

    private List<Vertex> getIntersectionPoints(OPolygon p, FoldLine line){
        final ArrayList<Vertex> vertexes = new ArrayList<>();
        vertexes.add(new Vertex());
        vertexes.add(new Vertex());
        return vertexes;
    }

    public static double cmpWithLine(FoldLine line, Vertex p){
        final double translatedK = line.getK() / Math.abs(line.getK()); // 1 or -1
        final double yOnLine = getYForLine(line, p.getFloatX());
        return Double.compare(yOnLine, p.getFloatY()) * translatedK;
    }

    private List<Vertex> revertPolygon(List<Vertex> polygon, FoldLine line){
        final ArrayList<Vertex> result = new ArrayList<Vertex>(polygon.size());
        for (Vertex vertex : polygon) {
            //d = (x + (y - c)*k ) / (1 + k*k )
            //x' = 2*d - x
            //y' = 2*d*k - y + 2*c

            final Fraction d = vertex.y.sub(line.getFractionC()).mul(line.getFractionK()).add(vertex.x).div(new Fraction(1, 1).add(line.getFractionK().mul(line.getFractionK())));


        }

        return result;
    }

}
