package com.teamdev.fold;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolderHelper {

    public FoldLine investigateFoldLine(){
        return new FoldLine(new Point2D.Float(0.0f, 0.5f), new Point2D.Float(1.0f, 0.5f));
    }

    public static Paper createInitialPaper(){
        final ArrayList<Point2D.Float> vertices = new ArrayList<>();
        vertices.add(new Point2D.Float(0,0));
        vertices.add(new Point2D.Float(1,0));
        vertices.add(new Point2D.Float(1,1));
        vertices.add(new Point2D.Float(0,1));
        return new Paper(vertices);
    }

}
