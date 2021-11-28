package io.github.nomeyho.conrec.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ConrecContour {
    private LinkedList<ConrecPoint> points; // TODO try to make final, do not modify second contour! 
    private boolean closed;

    public ConrecContour() {
        points = new LinkedList<>();
        closed = false;
    }

    public void addFirst(final ConrecPoint point) {
        if (point == null) {
            return;
        }

        points.addFirst(point);
    }

    public void addLast(final ConrecPoint point) {
        if (point == null) {
            return;
        }
        
        points.addLast(point);
    }

    // [1, 2, 3] + [4, 5] => [1, 2, 3, 4, 5]
    public void merge(final ConrecContour contour) {
        if (contour == null) {
            return;
        }

        points.addAll(contour.getPoints());
    }

    // [1, 2, 3] + [4, 5] => [1, 2, 3, 5, 4]
    public void mergeReversed(final ConrecContour contour) {
        if (contour == null) {
            return;
        }
        
        final int size = contour.getPoints().size();
        final ListIterator<ConrecPoint> it = contour.getPoints().listIterator(size);
        
        while (it.hasPrevious()) {
            points.addLast(it.previous());
        }
    }

    // [1, 2, 3] + [4, 5] => [3, 2, 1, 4, 5]
    public void reverseAndMerge(final ConrecContour contour) {
        if (contour == null) {
            return;
        }

        for (final ConrecPoint point : points) {
            contour.points.addFirst(point);
        }
        
        points = contour.points;
    }

    public ConrecPoint getStart() {
        if (points.isEmpty()) {
            return null;
        }

        return points.getFirst();
    }

    public ConrecPoint getEnd() {
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

    public List<ConrecPoint> getPoints() {
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
