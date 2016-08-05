package com.teamdev;

/**
 * Created by kirill.sidorchuk on 8/5/2016.
 */
public class FRange {
    float min;
    float max;

    public FRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "FRange{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
