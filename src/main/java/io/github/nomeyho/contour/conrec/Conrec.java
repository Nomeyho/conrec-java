package io.github.nomeyho.contour.conrec;

import io.github.nomeyho.contour.conrec.model.Level;
import io.github.nomeyho.contour.conrec.model.Point;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapted from:
 * http://paulbourke.net/papers/conrec/Conrec.java
 */
public class Conrec {

    private static final int[][][] CASTAB = {
            {{0, 0, 8}, {0, 2, 5}, {7, 6, 9}},
            {{0, 3, 4}, {1, 3, 1}, {4, 3, 0}},
            {{9, 6, 7}, {5, 2, 0}, {8, 0, 0}}
    };
    private static final int[] IM = {0, 1, 1, 0};
    private static final int[] JM = {0, 0, 1, 1};

    /**
     * Contour is a contouring subroutine for rectangularily spaced data
     *
     * @param x      data matrix column coordinates
     * @param y      data matrix row coordinates
     * @param z      matrix of data to contour
     * @param levels contour levels in increasing order
     * @return the computed contours for each level
     */
    public static List<Level> contour(double[] x, double[] y, double[][] z, double[] levels) {
        final List<Level> result = initLevels(levels);
        int m1, m2, m3;
        int case_value;
        double dMin, dMax;
        double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        int i, j, k, m;
        double[] h = new double[5], xh = new double[5], yh = new double[5];
        int[] sh = new int[5];

        for (j = y.length - 2; j >= 0; j--) {
            for (i = 0; i <= x.length - 2; i++) {
                dMin = min(z[i][j], z[i][j + 1], z[i + 1][j], z[i + 1][j + 1]);
                dMax = max(z[i][j], z[i][j + 1], z[i + 1][j], z[i + 1][j + 1]);

                if (dMax < levels[0] || dMin > levels[levels.length - 1]) {
                    continue;
                }

                for (k = 0; k < levels.length; k++) {
                    if (levels[k] < dMin || levels[k] > dMax) {
                        continue;
                    }

                    for (m = 4; m >= 0; m--) {
                        if (m > 0) {
                            h[m] = z[i + IM[m - 1]][j + JM[m - 1]] - levels[k];
                            xh[m] = x[i + IM[m - 1]];
                            yh[m] = y[j + JM[m - 1]];
                        } else {
                            h[0] = 0.25 * (h[1] + h[2] + h[3] + h[4]);
                            xh[0] = 0.5 * (x[i] + x[i + 1]);
                            yh[0] = 0.5 * (y[j] + y[j + 1]);
                        }
                        if (h[m] > 0.0) {
                            sh[m] = 1;
                        } else if (h[m] < 0.0) {
                            sh[m] = -1;
                        } else
                            sh[m] = 0;
                    }
                    for (m = 1; m <= 4; m++) {
                        m1 = m;
                        m2 = 0;
                        if (m != 4) {
                            m3 = m + 1;
                        } else {
                            m3 = 1;
                        }
                        case_value = CASTAB[sh[m1] + 1][sh[m2] + 1][sh[m3] + 1];
                        if (case_value != 0) {
                            switch (case_value) {
                                case 1: // Line between vertices 1 and 2
                                    x1 = xh[m1];
                                    y1 = yh[m1];
                                    x2 = xh[m2];
                                    y2 = yh[m2];
                                    break;
                                case 2: // Line between vertices 2 and 3
                                    x1 = xh[m2];
                                    y1 = yh[m2];
                                    x2 = xh[m3];
                                    y2 = yh[m3];
                                    break;
                                case 3: // Line between vertices 3 and 1
                                    x1 = xh[m3];
                                    y1 = yh[m3];
                                    x2 = xh[m1];
                                    y2 = yh[m1];
                                    break;
                                case 4: // Line between vertex 1 and side 2-3
                                    x1 = xh[m1];
                                    y1 = yh[m1];
                                    x2 = xSect(m2, m3, h, xh);
                                    y2 = ySect(m2, m3, h, yh);
                                    break;
                                case 5: // Line between vertex 2 and side 3-1
                                    x1 = xh[m2];
                                    y1 = yh[m2];
                                    x2 = xSect(m3, m1, h, xh);
                                    y2 = ySect(m3, m1, h, yh);
                                    break;
                                case 6: //  Line between vertex 3 and side 1-2
                                    x1 = xh[m3];
                                    y1 = yh[m3];
                                    x2 = xSect(m1, m2, h, xh);
                                    y2 = ySect(m1, m2, h, yh);
                                    break;
                                case 7: // Line between sides 1-2 and 2-3
                                    x1 = xSect(m1, m2, h, xh);
                                    y1 = ySect(m1, m2, h, yh);
                                    x2 = xSect(m2, m3, h, xh);
                                    y2 = ySect(m2, m3, h, yh);
                                    break;
                                case 8: // Line between sides 2-3 and 3-1
                                    x1 = xSect(m2, m3, h, xh);
                                    y1 = ySect(m2, m3, h, yh);
                                    x2 = xSect(m3, m1, h, xh);
                                    y2 = ySect(m3, m1, h, yh);
                                    break;
                                case 9: // Line between sides 3-1 and 1-2
                                    x1 = xSect(m3, m1, h, xh);
                                    y1 = ySect(m3, m1, h, yh);
                                    x2 = xSect(m1, m2, h, xh);
                                    y2 = ySect(m1, m2, h, yh);
                                    break;
                                default:
                                    break;
                            }

                            final Point p1 = new Point(x1, y1);
                            final Point p2 = new Point(x2, y2);
                            result.get(k).addSegment(p1, p2);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static List<Level> initLevels(final double[] levels) {
        return Arrays.stream(levels)
                .boxed()
                .map(Level::new)
                .collect(Collectors.toList());
    }

    private static double min(double m1, double m2, double m3, double m4) {
        return Math.min(Math.min(m1, m2), Math.min(m3, m4));
    }

    private static double max(double m1, double m2, double m3, double m4) {
        return Math.max(Math.max(m1, m2), Math.max(m3, m4));
    }

    private static double xSect(int p1, int p2, final double[] h, double[] xh) {
        return (h[p2] * xh[p1] - h[p1] * xh[p2]) / (h[p2] - h[p1]);
    }

    private static double ySect(int p1, int p2, final double[] h, double[] yh) {
        return (h[p2] * yh[p1] - h[p1] * yh[p2]) / (h[p2] - h[p1]);
    }
}
