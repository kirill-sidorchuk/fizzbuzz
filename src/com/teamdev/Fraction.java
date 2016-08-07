package com.teamdev;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by dmitriy.kuzmin on 8/6/2016.
 */
public class Fraction implements Comparable<Fraction> {
    public BigDecimal n;
    public BigDecimal d;

    public Fraction(long n, long d) {
        this.n = new BigDecimal(n);
        this.d = new BigDecimal(d);
    }

    public Fraction(BigDecimal n, BigDecimal d) {
        this.n = n;
        this.d = d;
    }

    public float getFloatValue() {
        return n.divide(d, 100, 1).floatValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        return Objects.equals(n, fraction.n) && Objects.equals(d, fraction.d);
    }

    public BigDecimal common_divider(BigDecimal a, BigDecimal b) {
        if (checkMod(a, 2) && checkMod(b, 2) ) return new BigDecimal(2);
        if (checkMod(a, 3) && checkMod(b, 3) ) return new BigDecimal(3);
        if (checkMod(a, 5) && checkMod(b, 5) ) return new BigDecimal(5);
        if (checkMod(a, 7) && checkMod(b, 7) ) return new BigDecimal(7);
        if (checkMod(a, 11) && checkMod(b, 11) ) return new BigDecimal(11);
        if (checkMod(a, 13) && checkMod(b, 13) ) return new BigDecimal(13);
        if (checkMod(a, 17) && checkMod(b, 17) ) return new BigDecimal(17);
        if (checkMod(a, 19) && checkMod(b, 19) ) return new BigDecimal(19);
        if (checkMod(a, 23) && checkMod(b, 23) ) return new BigDecimal(23);
        if (checkMod(a, 11) && checkMod(b, 11) ) return new BigDecimal(11);
        if (checkMod(a, 13) && checkMod(b, 13) ) return new BigDecimal(13);
        if (checkMod(a, 17) && checkMod(b, 17) ) return new BigDecimal(17);
        if (checkMod(a, 19) && checkMod(b, 19) ) return new BigDecimal(19);
        if (checkMod(a, 23) && checkMod(b, 23) ) return new BigDecimal(23);
        if (checkMod(a, 29) && checkMod(b, 29) ) return new BigDecimal(29);
        if (checkMod(a, 31) && checkMod(b, 31) ) return new BigDecimal(31);
        if (checkMod(a, 37) && checkMod(b, 37) ) return new BigDecimal(37);
        if (checkMod(a, 41) && checkMod(b, 41) ) return new BigDecimal(41);
        if (checkMod(a, 43) && checkMod(b, 43) ) return new BigDecimal(43);
        if (checkMod(a, 47) && checkMod(b, 47) ) return new BigDecimal(47);
        if (checkMod(a, 53) && checkMod(b, 53) ) return new BigDecimal(53);
        if (checkMod(a, 59) && checkMod(b, 59) ) return new BigDecimal(59);
        if (checkMod(a, 61) && checkMod(b, 61) ) return new BigDecimal(61);
        if (checkMod(a, 67) && checkMod(b, 67) ) return new BigDecimal(67);
        if (checkMod(a, 71) && checkMod(b, 71) ) return new BigDecimal(71);
        if (checkMod(a, 73) && checkMod(b, 73) ) return new BigDecimal(73);
        if (checkMod(a, 79) && checkMod(b, 79) ) return new BigDecimal(79);
        if (checkMod(a, 83) && checkMod(b, 83) ) return new BigDecimal(83);
        if (checkMod(a, 89) && checkMod(b, 89) ) return new BigDecimal(89);
        if (checkMod(a, 97) && checkMod(b, 97) ) return new BigDecimal(97);
        if (checkMod(a, 101) && checkMod(b, 101) ) return new BigDecimal(101);
        if (checkMod(a, 103) && checkMod(b, 103) ) return new BigDecimal(103);
        if (checkMod(a, 107) && checkMod(b, 107) ) return new BigDecimal(107);
        if (checkMod(a, 109) && checkMod(b, 109) ) return new BigDecimal(109);
        if (checkMod(a, 113) && checkMod(b, 113) ) return new BigDecimal(113);
        if (checkMod(a, 127) && checkMod(b, 127) ) return new BigDecimal(127);
        if (checkMod(a, 131) && checkMod(b, 131) ) return new BigDecimal(131);
        if (checkMod(a, 137) && checkMod(b, 137) ) return new BigDecimal(137);
        if (checkMod(a, 139) && checkMod(b, 139) ) return new BigDecimal(139);
        if (checkMod(a, 149) && checkMod(b, 149) ) return new BigDecimal(149);
        if (checkMod(a, 151) && checkMod(b, 151) ) return new BigDecimal(151);
        if (checkMod(a, 157) && checkMod(b, 157) ) return new BigDecimal(157);
        if (checkMod(a, 163) && checkMod(b, 163) ) return new BigDecimal(163);
        if (checkMod(a, 167) && checkMod(b, 167) ) return new BigDecimal(167);
        if (checkMod(a, 173) && checkMod(b, 173) ) return new BigDecimal(173);
        if (checkMod(a, 179) && checkMod(b, 179) ) return new BigDecimal(179);
        if (checkMod(a, 181) && checkMod(b, 181) ) return new BigDecimal(181);
        if (checkMod(a, 191) && checkMod(b, 191) ) return new BigDecimal(191);
        if (checkMod(a, 193) && checkMod(b, 193) ) return new BigDecimal(193);
        if (checkMod(a, 197) && checkMod(b, 197) ) return new BigDecimal(197);
        if (checkMod(a, 199) && checkMod(b, 199) ) return new BigDecimal(199);
        return new BigDecimal(1);
    }

    public Fraction normalize() {
        if (n.equals(new BigDecimal(0)) )
            d = new BigDecimal(1);
        else {
            //for (int i = 0; i < 20; ++i) {
            while(true){
                BigDecimal x = common_divider(n, d);
                if (x.equals(new BigDecimal(1)) ) break;
                n = n.divide(x);
                d = d.divide(x);
            }

            if (d.compareTo(new BigDecimal(0)) == -1) {
                d = d.multiply(new BigDecimal(-1));
                n = n.multiply(new BigDecimal(-1));
            }
        }
        return this;
    }

    public Fraction add(Fraction f) {
        BigDecimal _n = n.multiply(f.d).add(f.n.multiply(d));
        BigDecimal _d = d.multiply(f.d);
        return new Fraction(_n, _d).normalize();
    }

    public Fraction sub(Fraction f) {

        BigDecimal _n = n.multiply(f.d).subtract(f.n.multiply(d));
        BigDecimal _d = d.multiply(f.d);
        return new Fraction(_n, _d).normalize();
    }

    public Fraction mul(Fraction f) {
        return new Fraction(n.multiply(f.n) , d.multiply(f.d)).normalize();
    }

    public Fraction div(Fraction x) {
        return new Fraction(n.multiply(x.d) , d.multiply(x.n)).normalize();
    }

    public Fraction div(long v) {
        return new Fraction(n, d.multiply(new BigDecimal(v)));
    }

    @Override
    public int compareTo(Fraction o) {
        Fraction diff = sub(o);
        return diff.n.compareTo(new BigDecimal(0));
    }

    @Override
    public String toString() {
        return "Fraction{" + n + "/" + d + "}";
    }

    public Fraction inv() {
        return new Fraction(n.multiply(new BigDecimal(-1)), d);
    }

    public double getDoubleValue() {
        return n.divide(d, 100, 1).doubleValue();
    }

    private boolean checkMod(BigDecimal d, long n){
        return d.toBigInteger().mod(new BigDecimal(n).toBigInteger()).equals(new BigInteger("0"));
    }

}
