package io.github.nomeyho.conrec;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Represent an iso-contour.
 */
public class ConrecContour {
    private LinkedList<ConrecPoint> points; // TODO try to make final, do not modify second contour! 
    private boolean closed;

    ConrecContour() {
        points = new LinkedList<>();
        closed = false;
    }

    void addFirst(final ConrecPoint point) {
        if (point == null) {
            return;
        }

        points.addFirst(point);
    }

    void addLast(final ConrecPoint point) {
        if (point == null) {
            return;
        }

        points.addLast(point);
    }

    // [1, 2, 3] + [4, 5] => [1, 2, 3, 4, 5]
    void merge(final ConrecContour contour) {
        if (contour == null) {
            return;
        }

        points.addAll(contour.getPoints());
    }

    // [1, 2, 3] + [4, 5] => [1, 2, 3, 5, 4]
    void mergeReversed(final ConrecContour contour) {
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
    void reverseAndMerge(final ConrecContour contour) {
        if (contour == null) {
            return;
        }

        for (final ConrecPoint point : points) {
            contour.points.addFirst(point);
        }

        points = contour.points;
    }

    /**
     * Return the starting point of the contour
     *
     * @return the starting point
     */
    public ConrecPoint getStart() {
        if (points.isEmpty()) {
            return null;
        }

        return points.getFirst();
    }

    /**
     * Return the ending point of the contour
     *
     * @return the ending point
     */
    public ConrecPoint getEnd() {
        if (points.isEmpty()) {
            return null;
        }

        return points.getLast();
    }

    /**
     * Indicates if the contour is closed
     *
     * @return true if the starting point and the ending point are the same, false otherwise
     */
    public boolean isClosed() {
        return closed;
    }

    void setClosed(final boolean closed) {
        this.closed = closed;
    }

    /**
     * Return the list of points composing the contour.
     * The first element of the list corresponds to the starting point of the contour,
     * while the last element corresponds to the ending point of the contour.
     *
     * @return a list of points
     */
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
