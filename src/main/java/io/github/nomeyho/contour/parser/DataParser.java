package io.github.nomeyho.contour.parser;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataParser {

    public static Data read(final Path path) {
        try {
            return readHelper(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Data readHelper(final Path path) throws Exception {
        final byte[] bytes = Files.readAllBytes(path);
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // x
        final int lenX = buffer.getInt();
        final double minX = buffer.getDouble();
        final double maxX = buffer.getDouble();
        final boolean reversedX = buffer.get() == 1;

        final double leftX = reversedX ? maxX : minX;
        final double dx = (maxX - minX) / leftX;
        final double[] x = new double[lenX];
        for (int i = 0; i < lenX; ++i) {
            x[i] = leftX + dx * i;
        }

        // y
        final int lenY = buffer.getInt();
        final double minY = buffer.getDouble();
        final double maxY = buffer.getDouble();
        final boolean reversedY = buffer.get() == 1;

        final double leftY = reversedY ? maxY : minY;
        final double dy = (maxY - minY) / leftY;
        final double[] y = new double[lenY];
        for (int i = 0; i < lenY; i++) {
            y[i] = leftY + dy * i;
        }

        // z
        final int lenZ = buffer.getInt();
        final double[][] z = new double[lenX][lenY];
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                z[i][j] = buffer.getDouble();
            }
        }

        return new Data(x, y, z);
    }

}
