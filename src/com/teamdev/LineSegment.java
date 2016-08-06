package com.teamdev;

/**
 * Created by dmitriy.kuzmin on 8/5/2016.
 */
public class LineSegment {
    public Vertex v1;
    public Vertex v2;

    public LineSegment(String line) {
        int i = line.indexOf(' ');

        String v1Line = line.substring(0, i).trim();
        v1 = new Vertex(v1Line);

        String v2Line = line.substring(i+1).trim();
        v2 = new Vertex(v2Line);
    }

    @Override
    public String toString() {
        String result = "";

        String vdx1_str = "";
        if (v1.x.d != 1) {
            vdx1_str = "/" + v1.x.d;
        }
        String vdy1_str = "";
        if (v1.y.d != 1) {
            vdy1_str = "/" + v1.y.d;
        }
        String vdx2_str = "";
        if (v2.x.d != 1) {
            vdx2_str = "/" + v2.x.d;
        }
        String vdy2_str = "";
        if (v2.y.d != 1) {
            vdy2_str = "/" + v2.y.d;
        }
        result += v1.x.n + vdx1_str + "," + v1.y.n + vdy1_str + " "
                + v2.x.n + vdx2_str + "," + v2.y.n + vdy2_str;

        return result;
    }
}
