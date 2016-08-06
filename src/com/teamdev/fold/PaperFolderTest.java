package com.teamdev.fold;

import com.teamdev.Fraction;
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
        //testRandomFold();

        /*final FoldLine foldLine = new FoldLine(new Vertex(0, 1, 1, 1), new Vertex(1, 1, 0, 1));
        System.out.println(cmpWithLine(foldLine, new Vertex(1, 1, 0, 1)));
        System.out.println("line: k=" + foldLine.getFractionK() + " c=" + foldLine.getFractionC());*/


        //System.out.println(cmpWithLine(new FoldLine(1, 0), new Vertex(1, 2, 1, 2)));

        final PaperFolder paperFolder = new PaperFolder();
        final PaperVisualizer paperVisualizer = new PaperVisualizer();
        final PaperFolderHelper paperFolderHelper = new PaperFolderHelper();

        final Paper paper = createInitialPaper();
        paperVisualizer.draw(paper, new File(FOLDER + "1.png"));

        //final FoldLine foldLine = new FoldLine(new Vertex(15, 32, 24, 32), new Vertex(30, 32, 10, 32));
        final FoldLine foldLine = new FoldLine(new Fraction(-9,10), new Fraction(1,5));
        paperVisualizer.draw(paper, foldLine, new File(FOLDER + "2.png"));

        final Paper folded = paperFolder.fold(paper, foldLine);
        paperVisualizer.draw(folded, new File(FOLDER + "3.png"));

        //final FoldLine foldLine2 = new FoldLine(new Vertex(1, 1, 1, 3), new Vertex(0, 1, 2, 3));
        final FoldLine foldLine2 = new FoldLine(new Fraction(-4,5), new Fraction(3,10));
        paperVisualizer.draw(folded, foldLine2, new File(FOLDER + "4.png"));

        final Paper folded2 = paperFolder.fold(folded, foldLine2);
        paperVisualizer.draw(folded2, new File(FOLDER + "5.png"));

        //getPolygonArea
    }

    public static void testRandomFold(){
        final PaperFolder paperFolder = new PaperFolder();
        final PaperVisualizer paperVisualizer = new PaperVisualizer();
        final PaperFolderHelper paperFolderHelper = new PaperFolderHelper();

        Paper paper = createInitialPaper();

        int n = 0;
        for(int i=0; i<3; i++){
            paperVisualizer.draw(paper, new File(FOLDER + (n++) + ".png"));
            final FoldLine foldLine = paperFolderHelper.investigateFoldLine();
            paperVisualizer.draw(paper, foldLine, new File(FOLDER + (n++) + ".png"));
            paper = paperFolder.fold(paper, foldLine);
        }

    }


}
