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
        if ((a % 2) == 0 && (b % 2) == 0) return 2;
        if ((a % 3) == 0 && (b % 3) == 0) return 3;
        if ((a % 5) == 0 && (b % 5) == 0) return 5;
        if ((a % 7) == 0 && (b % 7) == 0) return 7;
        if ((a % 11) == 0 && (b % 11) == 0) return 11;
        if ((a % 13) == 0 && (b % 13) == 0) return 13;
        if ((a % 17) == 0 && (b % 17) == 0) return 17;
        if ((a % 19) == 0 && (b % 19) == 0) return 19;
        if ((a % 23) == 0 && (b % 23) == 0) return 23;
        if ((a % 11) == 0 && (b % 11) == 0) return 11;
        if ((a % 13) == 0 && (b % 13) == 0) return 13;
        if ((a % 17) == 0 && (b % 17) == 0) return 17;
        if ((a % 19) == 0 && (b % 19) == 0) return 19;
        if ((a % 23) == 0 && (b % 23) == 0) return 23;
        if ((a % 29) == 0 && (b % 29) == 0) return 29;
        if ((a % 31) == 0 && (b % 31) == 0) return 31;
        if ((a % 37) == 0 && (b % 37) == 0) return 37;
        if ((a % 41) == 0 && (b % 41) == 0) return 41;
        if ((a % 43) == 0 && (b % 43) == 0) return 43;
        if ((a % 47) == 0 && (b % 47) == 0) return 47;
        if ((a % 53) == 0 && (b % 53) == 0) return 53;
        if ((a % 59) == 0 && (b % 59) == 0) return 59;
        if ((a % 61) == 0 && (b % 61) == 0) return 61;
        if ((a % 67) == 0 && (b % 67) == 0) return 67;
        if ((a % 71) == 0 && (b % 71) == 0) return 71;
        if ((a % 73) == 0 && (b % 73) == 0) return 73;
        if ((a % 79) == 0 && (b % 79) == 0) return 79;
        if ((a % 83) == 0 && (b % 83) == 0) return 83;
        if ((a % 89) == 0 && (b % 89) == 0) return 89;
        if ((a % 97) == 0 && (b % 97) == 0) return 97;
        if ((a % 101) == 0 && (b % 101) == 0) return 101;
        if ((a % 103) == 0 && (b % 103) == 0) return 103;
        if ((a % 107) == 0 && (b % 107) == 0) return 107;
        if ((a % 109) == 0 && (b % 109) == 0) return 109;
        if ((a % 113) == 0 && (b % 113) == 0) return 113;
        if ((a % 127) == 0 && (b % 127) == 0) return 127;
        if ((a % 131) == 0 && (b % 131) == 0) return 131;
        if ((a % 137) == 0 && (b % 137) == 0) return 137;
        if ((a % 139) == 0 && (b % 139) == 0) return 139;
        if ((a % 149) == 0 && (b % 149) == 0) return 149;
        if ((a % 151) == 0 && (b % 151) == 0) return 151;
        if ((a % 157) == 0 && (b % 157) == 0) return 157;
        if ((a % 163) == 0 && (b % 163) == 0) return 163;
        if ((a % 167) == 0 && (b % 167) == 0) return 167;
        if ((a % 173) == 0 && (b % 173) == 0) return 173;
        if ((a % 179) == 0 && (b % 179) == 0) return 179;
        if ((a % 181) == 0 && (b % 181) == 0) return 181;
        if ((a % 191) == 0 && (b % 191) == 0) return 191;
        if ((a % 193) == 0 && (b % 193) == 0) return 193;
        if ((a % 197) == 0 && (b % 197) == 0) return 197;
        if ((a % 199) == 0 && (b % 199) == 0) return 199;
        return 1;
    }

    public Fraction normalize() {
        if (n == 0)
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
        return new Fraction(n * f.n, d * f.d).normalize();
    }

    public Fraction div(Fraction x) {
        return new Fraction(n * x.d, d * x.n).normalize();
    }

    public Fraction div(long v) {
        return new Fraction(n, d * v);
    }

    @Override
    public int compareTo(Fraction o) {
        Fraction diff = sub(o);
        if (diff.n == 0) return 0;
        return diff.n < 0 ? -1 : +1;
    }

    @Override
    public String toString() {
        return "Fraction{" + n + "/" + d + "}";
    }

    public Fraction inv() {
        return new Fraction(-n, d);
    }

    public double getDoubleValue() {
        return (double) n / (double) d;
    }

}
