package com.teamdev.fold;

import com.teamdev.Vertex;

import java.io.File;

import static com.teamdev.fold.PaperFolder.cmpWithLine;
import static com.teamdev.fold.PaperFolderHelper.createInitialPaper;

/**
 * @author Vladislav Kovchug
 */
public class PaperFolderTest {

    public static final String FOLDER = "fold/";

    public static void main(String[] args) {
        System.out.println(cmpWithLine(new FoldLine(new Vertex(-1, 1, -1, 1), new Vertex(0, 1, 0, 1)), new Vertex(1, 2, 1, 2)));
        System.out.println(cmpWithLine(new FoldLine(1, 0), new Vertex(1, 2, 1, 2)));

        final PaperFolder paperFolder = new PaperFolder();
        final PaperVisualizer paperVisualizer = new PaperVisualizer();
        final PaperFolderHelper paperFolderHelper = new PaperFolderHelper();

        final Paper paper = createInitialPaper();
        paperVisualizer.draw(paper, new File(FOLDER + "1.png"));

        final FoldLine foldLine = paperFolderHelper.investigateFoldLine();
        paperVisualizer.draw(paper, foldLine, new File(FOLDER + "2.png"));


        final Paper folded = paperFolder.fold(paper, foldLine);
        paperVisualizer.draw(folded, new File(FOLDER + "3.png"));

        final FoldLine foldLine2 = new FoldLine(new Vertex(1, 1, 0, 1), new Vertex(1, 2, 1, 2));
        paperVisualizer.draw(folded, foldLine2, new File(FOLDER + "4.png"));

        final Paper folded2 = paperFolder.fold(folded, foldLine2);
        paperVisualizer.draw(folded2, new File(FOLDER + "5.png"));

        //getPolygonArea
    }


}
