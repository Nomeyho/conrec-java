package io.github.nomeyho.conrec;

import java.util.Objects;

/**
 * Represents a point in a 2D space.
 */
public class ConrecPoint {

    private final double x;
    private final double y;
    
    public ConrecPoint(final double x, final double y) {
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
        if (!(point instanceof ConrecPoint)) {
            return false;
        }

        return x == ((ConrecPoint) point).x && y == ((ConrecPoint) point).y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
