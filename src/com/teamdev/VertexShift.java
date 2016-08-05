package com.teamdev;

import java.math.BigDecimal;

/**
 * Created by Samsonov on 8/5/2016.
 */
public class VertexShift {
    public BigDecimal nX; // nominator
    public BigDecimal dX; // denominator

    public BigDecimal nY; // nominator
    public BigDecimal dY; // denominator

    public VertexShift(BigDecimal nX, BigDecimal dX, BigDecimal nY, BigDecimal dY) {
        this.nX = nX;
        this.dX = dX;
        this.nY = nY;
        this.dY = dY;
    }
}
