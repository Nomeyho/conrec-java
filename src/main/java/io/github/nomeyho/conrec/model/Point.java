package io.github.nomeyho.conrec.model;

import java.util.Objects;

public class Point {

    private final double x;
    private final double y;
    
    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

        return x == ((Point) point).x && y == ((Point) point).y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
