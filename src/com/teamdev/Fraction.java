package com.teamdev;

/**
 * Created by dmitriy.kuzmin on 8/6/2016.
 */
public class Fraction implements Comparable<Fraction> {
    public long n;
    public long d;

    public Fraction(long n, long d) {
        this.n = n;
        this.d = d;
    }

    public float getFloatValue() {
        return (float) ((double) n / (double) d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        return n == fraction.n && d == fraction.d;
    }

    @Override
    public int hashCode() {
        int result = (int) (n ^ (n >>> 32));
        result = 31 * result + (int) (d ^ (d >>> 32));
        return result;
    }

    public long common_divider(long a, long b) {
        if( (a % 2) == 0 && (b % 2) == 0 ) return 2;
        if( (a % 3) == 0 && (b % 3) == 0 ) return 3;
        if( (a % 5) == 0 && (b % 5) == 0 ) return 5;
        if( (a % 7) == 0 && (b % 7) == 0 ) return 7;
        if( (a % 11) == 0 && (b % 11) == 0 ) return 11;
        if( (a % 13) == 0 && (b % 13) == 0 ) return 13;
        if( (a % 17) == 0 && (b % 17) == 0 ) return 17;
        if( (a % 19) == 0 && (b % 19) == 0 ) return 19;
        if( (a % 23) == 0 && (b % 23) == 0 ) return 23;
        return 1;
    }

    public Fraction normalize() {
        if( n == 0 )
            d = 1;
        else {
            for (int i = 0; i < 20; ++i) {
                long x = common_divider(n, d);
                if (x == 1) break;
                n /= x;
                d /= x;
            }

            if (d < 0) {
                d = -d;
                n = -n;
            }
        }
        return this;
    }

    public Fraction add(Fraction f) {
        long _n = n * f.d + f.n * d;
        long _d = d * f.d;
        return new Fraction(_n, _d).normalize();
    }

    public Fraction sub(Fraction f) {
        long _n = n * f.d - f.n * d;
        long _d = d * f.d;
        return new Fraction(_n, _d).normalize();
    }

    public Fraction mul(Fraction f) {
        return new Fraction(n*f.n, d*f.d).normalize();
    }

    public Fraction div(Fraction x){
        return new Fraction(n*x.d, d*x.n).normalize();
    }

    public Fraction div(long v) {
        return new Fraction(n, d*v);
    }

    @Override
    public int compareTo(Fraction o) {
        Fraction diff = sub(o);
        if( diff.n == 0 ) return 0;
        return diff.n < 0 ? -1 : +1;
    }

    @Override
    public String toString() {
        return "Fraction{" + n + "/" + d + "}";
    }

    public Fraction inv() {
        return new Fraction(-n,d);
    }

    public double getDoubleValue() {
        return (double)n / (double)d;
    }

}
