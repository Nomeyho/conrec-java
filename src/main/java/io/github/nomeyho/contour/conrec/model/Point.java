package io.github.nomeyho.contour.conrec.model;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

    private double x;
    private double y;

    public Point() {
        this(0, 0);
    }

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
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
