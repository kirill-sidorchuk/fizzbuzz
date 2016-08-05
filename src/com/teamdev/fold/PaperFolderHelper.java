package com.teamdev.fold;

import com.teamdev.Vertex;

import java.util.ArrayList;
import java.util.List;

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
        final ArrayList<List<Vertex>> objects = new ArrayList<>();
        objects.add(vertices);
        return new Paper(objects);
    }

}
