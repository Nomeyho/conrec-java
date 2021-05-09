package io.github.nomeyho.contour.msquare.model;

import java.util.Objects;

public class Point {

    private final double x;
    private final double y;
    private final double z;

    public Point() {
        this(0, 0, 0);
    }

    public Point(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(final Object point) {
        if (!(point instanceof Point)) {
            return false;
        }

        return x == ((Point) point).x && y == ((Point) point).y && z == ((Point) point).z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + "," + z + ")";
    }
}
