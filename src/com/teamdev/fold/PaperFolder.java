package com.teamdev.fold;


import com.teamdev.Fraction;
import com.teamdev.OPolygon;
import com.teamdev.Vertex;
import com.teamdev.triangulation.LineHelper;

import java.util.ArrayList;
import java.util.Collections;
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
            //final List<Vertex> myIntersectionsPoints = getIntersectionsPoints(polygon, line);
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
                    leftPolygon.add(v);
                } else if(cmpWithLine(line, v) > 0){
                    rightPolygon.add(v);
                }
            }

            rightPolygon.add(topPoint);
            leftPolygon.add(bottomPoint);
            result.add(new OPolygon(rightPolygon));
            //result.add(new OPolygon(revertPolygon(leftPolygon, line)));
        }

        return result;
    }

    public static double cmpWithLine(FoldLine line, Vertex p){
        final double translatedK = line.getK() >= 0 ? 1 : -1; // 1 or -1
        final double yOnLine = getYForLine(line, p.getFloatX());
        return Double.compare(yOnLine, p.getFloatY()) * translatedK;
    }

    private List<Vertex> getIntersectionsPoints(OPolygon polygon, FoldLine line){
        final ArrayList<Vertex> result = new ArrayList<>();

        for(int i=0; i<polygon.vertices.size(); i++){
            final Vertex v1 = polygon.vertices.get(i);
            final Vertex v2 = i+1 < polygon.vertices.size() ? polygon.vertices.get(i+1) : polygon.vertices.get(0);
            final FoldLine line2 = new FoldLine(v1, v2);

            final Fraction c1 = line.getFractionC();
            final Fraction c2 = line2.getFractionC();
            final Fraction k1 = line.getFractionK();
            final Fraction k2 = line2.getFractionK();

            if(k1.equals(k2)){ //they are parallel
                continue;
            }

            final Fraction x = c2.sub(c1).div(k1.sub(k2));
            final Fraction y = k1.mul(c2.sub(c1)).div(k1.sub(k2)).add(c1);

            final float minX = v1.getFloatX() <= v2.getFloatX() ? v1.getFloatX() : v2.getFloatX();
            final float maxX = v1.getFloatX() > v2.getFloatX() ? v1.getFloatX() : v2.getFloatX();
            final float minY = v1.getFloatY() <= v2.getFloatY() ? v1.getFloatY() : v2.getFloatY();
            final float maxY = v1.getFloatY() > v2.getFloatY() ? v1.getFloatY() :v2.getFloatY();

            if (x.getFloatValue() <= maxX && x.getFloatValue() >= minX &&
                y.getFloatValue() <= maxY && y.getFloatValue() >= minY){
                result.add(new Vertex(x, y));
            }
        }

        return result;
    }

    private List<Vertex> revertPolygon(List<Vertex> polygon, FoldLine line){
        final ArrayList<Vertex> result = new ArrayList<Vertex>(polygon.size());
        for (Vertex vertex : polygon) {
            final Fraction x = vertex.x;
            final Fraction y = vertex.y;
            final Fraction c = line.getFractionC();
            final Fraction k = line.getFractionK();

            //d = (x + (y - c)*k ) / (1 + k*k )
            //x' = 2*d - x
            //y' = 2*d*k - y + 2*c

            final Fraction d = y.sub(c).mul(k).add(x).div(new Fraction(1, 1).add(k.mul(k)));
            final Fraction newX = new Fraction(2, 1).mul(d).sub(x);
            final Fraction newY = new Fraction(2, 1).mul(d).mul(k).sub(y).add(new Fraction(2, 1).mul(c));
            result.add(new Vertex(newX, newY));
        }

        return result;
    }

}
