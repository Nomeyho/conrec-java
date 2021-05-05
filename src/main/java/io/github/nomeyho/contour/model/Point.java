package io.github.nomeyho.contour.model;

import java.io.Serializable;

public class Point implements Serializable {

    private static final double PRECISION = 10e-6;
    
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

    public boolean equal(final Point point) {
        if (point == null) {
            return false;
        }

        final double dx = this.x - point.x;
        final double dy = this.y - point.y;

        return (dx * dx + dy * dy) < PRECISION;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
