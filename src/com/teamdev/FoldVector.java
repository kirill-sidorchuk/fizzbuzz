package com.teamdev;

/**
 * Created by kirill.sidorchuk on 8/7/2016.
 */
public class FoldVector {
    public Vertex base;
    public Vertex direction;

    public FoldVector(Vertex base, Vertex direction) {
        this.base = base;
        this.direction = direction;
    }

    public static FoldVector fromVertices(Vertex A, Vertex B) {
        return new FoldVector(A, B.sub(A));
    }

    public boolean isFlips(Vertex V) {
        return direction.vect_prod_z(V.sub(base)).n < 0;
    }
}
