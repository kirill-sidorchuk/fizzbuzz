package com.teamdev;

/**
 * Created by dmitriy.kuzmin on 8/6/2016.
 */
public class Fraction {
    public final long n;
    public final long d;

    public Fraction(long n, long d) {
        this.n = n;
        this.d = d;
    }

    public float getFloatValue() {
        return (float) ((double) n / (double) d);
    }
}
