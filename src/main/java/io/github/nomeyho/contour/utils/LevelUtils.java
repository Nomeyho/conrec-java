package io.github.nomeyho.contour.utils;

import io.github.nomeyho.contour.parser.Data;

public final class LevelUtils {

    private LevelUtils() {

    }
    
    public static double[] levels(final Data data) {
        double[][] z = data.getZ();
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int i = 0; i < z.length; ++i) {
            for (int j = 0; j < z[0].length; j++) {
                min = Math.min(min, z[i][j]);
                max = Math.max(max, z[i][j]);
            }
        }

        double[] levels = new double[10];
        double delta = (max - min) / levels.length;
        for (int i = 0; i < levels.length; ++i) {
            levels[i] = min + delta * i;
        }
        return levels;
    }
}
