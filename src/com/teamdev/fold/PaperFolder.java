package com.teamdev.fold;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolder {

    public Paper fold(Paper paper, FoldLine line){
        final List<Point2D.Float> vertices = paper.getVertices();
        final ArrayList<Point2D.Float> leftPolygon = new ArrayList<>();
        final ArrayList<Point2D.Float> rightPolygon = new ArrayList<>();

        //select bottom and top point of line
        Point2D.Float bottomPoint = line.getV1().getY() <= line.getV2().getY() ? line.getV1() : line.getV2();
        Point2D.Float topPoint = line.getV1().getY() > line.getV2().getY() ? line.getV1() : line.getV2();

        rightPolygon.add(bottomPoint);
        leftPolygon.add(topPoint);
        for (Point2D.Float v : vertices) {
            if(cmpWithLine(line, v) < 0){ // if on the right side, push to
                rightPolygon.add(v);
            } else if(cmpWithLine(line, v) > 0){
                leftPolygon.add(v);
            }
        }

        rightPolygon.add(topPoint);
        leftPolygon.add(bottomPoint);

        return new Paper(rightPolygon);
    }

    public static double cmpWithLine(FoldLine line, Point2D p){
        final Point2D.Float v1 = line.getV1().getY() <= line.getV2().getY() ? line.getV1() : line.getV2();
        final Point2D.Float v2 = line.getV1().getY() > line.getV2().getY() ? line.getV1() : line.getV2();

        return ((v2.getX() - v1.getX())*(p.getY() - v1.getY()) - (v2.getY() - v1.getY())*(p.getX() - v1.getX()));
    }

}
