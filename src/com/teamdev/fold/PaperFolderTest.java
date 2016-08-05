package com.teamdev.fold;

import java.awt.geom.Point2D;
import java.io.File;

import static com.teamdev.fold.PaperFolder.cmpWithLine;
import static com.teamdev.fold.PaperFolderHelper.createInitialPaper;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolderTest {

    public static final String FOLDER = "fold/";

    public static void main(String[] args) {
        System.out.println(cmpWithLine(new FoldLine(new Point2D.Float(1, 1), new Point2D.Float(0, 0)), new Point2D.Float(0.3f, 0.2f)));

        final PaperFolder paperFolder = new PaperFolder();
        final PaperVisualizer paperVisualizer = new PaperVisualizer();
        final PaperFolderHelper paperFolderHelper = new PaperFolderHelper();

        final Paper paper = createInitialPaper();
        paperVisualizer.draw(paper, new File(FOLDER + "1.png"));

        final FoldLine foldLine = paperFolderHelper.investigateFoldLine();
        paperVisualizer.draw(paper, foldLine, new File(FOLDER + "2.png"));


        final Paper folded = paperFolder.fold(paper, foldLine);
        paperVisualizer.draw(folded, new File(FOLDER + "3.png"));

        final FoldLine foldLine2 = new FoldLine(new Point2D.Float(1.0f, 0.0f), new Point2D.Float(0.5f, 0.5f));
        paperVisualizer.draw(folded, foldLine2, new File(FOLDER + "4.png"));

        final Paper folded2 = paperFolder.fold(folded, foldLine2);
        paperVisualizer.draw(folded2, new File(FOLDER + "5.png"));

        //getPolygonArea
    }


}
