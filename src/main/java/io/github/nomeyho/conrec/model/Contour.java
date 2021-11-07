package io.github.nomeyho.conrec.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Contour implements Serializable {
    private LinkedList<Point> points; // TODO try to make final, do not modify second contour! 
    private boolean closed;

    public Contour() {
        points = new LinkedList<>();
        closed = false;
    }

    public void addFirst(final Point point) {
        if (point == null) {
            return;
        }

        points.addFirst(point);
    }

    public void addLast(final Point point) {
        if (point == null) {
            return;
        }
        
        points.addLast(point);
    }

    // [1, 2, 3] + [4, 5] => [1, 2, 3, 4, 5]
    public void merge(final Contour contour) {
        if (contour == null) {
            return;
        }

        points.addAll(contour.getPoints());
    }

    // [1, 2, 3] + [4, 5] => [1, 2, 3, 5, 4]
    public void mergeReversed(final Contour contour) {
        if (contour == null) {
            return;
        }
        
        final int size = contour.getPoints().size();
        final ListIterator<Point> it = contour.getPoints().listIterator(size);
        
        while (it.hasPrevious()) {
            points.addLast(it.previous());
        }
    }

    // [1, 2, 3] + [4, 5] => [3, 2, 1, 4, 5]
    public void reverseAndMerge(final Contour contour) {
        if (contour == null) {
            return;
        }

        for (final Point point : points) {
            contour.points.addFirst(point);
        }
        
        points = contour.points;
    }

    public Point getStart() {
        if (points.isEmpty()) {
            return null;
        }

        return points.getFirst();
    }

    public Point getEnd() {
        if (points.isEmpty()) {
            return null;
        }

        return points.getLast();
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(final boolean closed) {
        this.closed = closed;
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Contour{" +
                "points=" + points +
                ", closed=" + closed +
                '}';
    }
}
