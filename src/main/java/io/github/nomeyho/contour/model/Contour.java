package io.github.nomeyho.contour.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Contour implements Serializable {
    private LinkedList<Point> points;
    private boolean closed;

    public Contour() {
        this.points = new LinkedList<>();
        this.closed = false;
    }

    public void addBack(final Point point) {
        this.points.addLast(point);
    }

    public void addFront(final Point point) {
        this.points.addFirst(point);
    }

    public void concat(final Contour contour) {
        if (contour != null) {
            this.points.addAll(contour.getPoints());
        }
    }

    public Point getStart() {
        if (this.points.isEmpty()) {
            return null;
        }

        return this.points.getFirst();
    }

    public Point getEnd() {
        if (this.points.isEmpty()) {
            return null;
        }

        return this.points.getLast();
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(final boolean closed) {
        this.closed = closed;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public void reverse() {
        Collections.reverse(this.points);
    }

    @Override
    public String toString() {
        return "Contour{" +
                "points=" + points +
                ", closed=" + closed +
                '}';
    }
}
