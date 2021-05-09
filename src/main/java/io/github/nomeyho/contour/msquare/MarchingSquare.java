package io.github.nomeyho.contour.msquare;


import io.github.nomeyho.contour.msquare.model.Cell;
import io.github.nomeyho.contour.msquare.model.Point;

public class MarchingSquare {

    public static void contour(final double[] x, final double[] y, final double[][] z, final double[] levels) {
        final int I = x.length - 1;
        final int J = y.length - 1;
        final double level = levels[0]; // TODO multi level
        final Cell[][] cells = new Cell[I][J];

        // Build cells
        for (int i = 0; i < I; i++) {
            for (int j = 0; j < J; j++) {
                final Point topLeft = new Point(x[i + 1], y[j], z[i + 1][j]);
                final Point topRight = new Point(x[i + 1], y[j + 1], z[i + 1][j + 1]);
                final Point bottomRight = new Point(x[i], y[j + 1], z[i][j + 1]);
                final Point bottomLeft = new Point(x[i], y[j], z[i][j]);

                cells[i][j] = new Cell(topLeft, topRight, bottomRight, bottomLeft, level);
            }
        }

        // Create contours
        for (int i = 0; i < I; i++) {
            for (int j = 0; j < J; j++) {
                final Cell cell = cells[i][j];
                if (cell != null && !cell.isTrivial() && !cell.isSaddle()) {

                }
            }
        }
    }

}