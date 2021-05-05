package io.github.nomeyho.contour.parser;

import java.io.Serializable;

public class Data implements Serializable {

    private final double[] x;
    private final double[] y;
    private final double[][] z;

    public Data(final double[] x, final double[] y, final double[][] z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double[][] getZ() {
        return z;
    }
}
