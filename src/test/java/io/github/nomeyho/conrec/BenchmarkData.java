package io.github.nomeyho.conrec;

import java.util.Arrays;

public class BenchmarkData {

    private final String filename;
    private final double[] x;
    private final double[] y;
    private final double[][] z;

    public BenchmarkData(final String filename, final double[] x, final double[] y, final double[][] z) {
        this.filename = filename;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getFilename() {
        return filename;
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

    public double[] levels(final int numberLevels) {
        final double min = Arrays.stream(z).flatMapToDouble(Arrays::stream).min().orElseThrow();
        final double max = Arrays.stream(z).flatMapToDouble(Arrays::stream).max().orElseThrow();
        final double delta = (max - min) / numberLevels;

        final double[] levels = new double[numberLevels];
        for (int i = 0; i < numberLevels; ++i) {
            levels[i] = min + delta * i;
        }
        return levels;
    }
}
