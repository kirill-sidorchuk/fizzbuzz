package com.teamdev;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy.kuzmin on 8/6/2016.
 */
public class Fraction implements Comparable<Fraction> {

    static List<BigInteger> primes = new ArrayList<>();

    static {
        primes.add(BigInteger.valueOf(2));
        primes.add(BigInteger.valueOf(5));
        primes.add(BigInteger.valueOf(7));
        primes.add(BigInteger.valueOf(11));
        primes.add(BigInteger.valueOf(13));
        primes.add(BigInteger.valueOf(17));
        primes.add(BigInteger.valueOf(19));
        primes.add(BigInteger.valueOf(23));
        primes.add(BigInteger.valueOf(29));
        primes.add(BigInteger.valueOf(31));
        primes.add(BigInteger.valueOf(37));
        primes.add(BigInteger.valueOf(41));
        primes.add(BigInteger.valueOf(43));
        primes.add(BigInteger.valueOf(53));
        primes.add(BigInteger.valueOf(59));
        primes.add(BigInteger.valueOf(61));
        primes.add(BigInteger.valueOf(67));
        primes.add(BigInteger.valueOf(71));
        primes.add(BigInteger.valueOf(73));
        primes.add(BigInteger.valueOf(79));
        primes.add(BigInteger.valueOf(83));
        primes.add(BigInteger.valueOf(97));
        primes.add(BigInteger.valueOf(101));
        primes.add(BigInteger.valueOf(103));
        primes.add(BigInteger.valueOf(107));
        primes.add(BigInteger.valueOf(109));
        primes.add(BigInteger.valueOf(113));
        primes.add(BigInteger.valueOf(127));
        primes.add(BigInteger.valueOf(131));
        primes.add(BigInteger.valueOf(137));
        primes.add(BigInteger.valueOf(139));
        primes.add(BigInteger.valueOf(149));
        primes.add(BigInteger.valueOf(151));
        primes.add(BigInteger.valueOf(157));
        primes.add(BigInteger.valueOf(163));
        primes.add(BigInteger.valueOf(167));
        primes.add(BigInteger.valueOf(173));
        primes.add(BigInteger.valueOf(179));
        primes.add(BigInteger.valueOf(181));
        primes.add(BigInteger.valueOf(191));
        primes.add(BigInteger.valueOf(193));
        primes.add(BigInteger.valueOf(199));
    }

    public BigInteger n;
    public BigInteger d; // must be positive

    public Fraction(BigInteger n, BigInteger d) {
        this.n = n;
        this.d = d;
    }

    public Fraction(long n, long d) {
        this.n = BigInteger.valueOf(n);
        this.d = BigInteger.valueOf(d);
    }

    public Fraction(long n) {
        this.n = BigInteger.valueOf(n);
        this.d = BigInteger.ONE;
    }

    public Fraction() {
        this.n = BigInteger.ZERO;
        this.d = BigInteger.ONE;
    }

    public float getFloatValue()
    {
        return n.floatValue() / d.floatValue();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        return n.equals(fraction.n) && d.equals(fraction.d);
    }

    @Override
    public int hashCode() {
        int result = n != null ? n.hashCode() : 0;
        result = 31 * result + (d != null ? d.hashCode() : 0);
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


    public BigInteger common_divider(BigInteger a, BigInteger b) {
        for (BigInteger prime : primes) {
            if( a.mod(prime).equals(BigInteger.ZERO) &&
                b.mod(prime).equals(BigInteger.ZERO))
                return prime;
        }
        return BigInteger.ONE;
    }

    public Fraction normalize() {
        if (n.equals(BigInteger.ZERO))
            d = BigInteger.ONE;
        else {
            while (true){
                BigInteger x = common_divider(n, d);
                if (x.equals(BigInteger.ONE)) break;
                n = n.divide(x);
                d = d.divide(x);
            }

            if (d.compareTo(BigInteger.ZERO) < 0) {
                d = d.negate();
                n = n.negate();
            }
        }
        return this;
    }

    public Fraction add(Fraction f) {
        BigInteger _n = n.multiply(f.d).add(f.n.multiply(d));
        BigInteger _d = d.multiply(f.d);
        return new Fraction(_n, _d).normalize();
    }

    public Fraction sub(Fraction f) {
        BigInteger _n = n.multiply(f.d).subtract(f.n.multiply(d));
        BigInteger _d = d.multiply(f.d);
        return new Fraction(_n, _d).normalize();
    }

    public Fraction mul(Fraction f) {
        return new Fraction(n.multiply(f.n), d.multiply(f.d)).normalize();
    }

    public Fraction div(Fraction x) {
        return new Fraction(n.multiply(x.d), d.multiply(x.n)).normalize();
    }

    public Fraction div(long v) {
        return new Fraction(n, d.multiply(BigInteger.valueOf(v)));
    }

    public Fraction abs() {
        return new Fraction(n.abs(), d);
    }

    @Override
    public int compareTo(Fraction o) {
        Fraction diff = sub(o);
        return diff.n.compareTo(BigInteger.ZERO);
    }

    @Override
    public String toString() {
        return "Fraction{" + n.toString() + "/" + d.toString() + "}";
    }

    public Fraction inv() {
        return new Fraction(n.negate(), d);
    }

    public double getDoubleValue() {
        return n.doubleValue() / d.doubleValue();
    }

}
