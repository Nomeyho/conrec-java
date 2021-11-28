package io.github.nomeyho.conrec;

import java.util.*;

/**
 * Represent an iso-level.
 */
public class ConrecLevel {

    private final double z;
    private final Set<ConrecContour> contours;
    private final Map<ConrecPoint, ConrecContour> contourStarts;
    private final Map<ConrecPoint, ConrecContour> contourEnds;

    ConrecLevel(final double z) {
        this.z = z;
        this.contours = new HashSet<>();
        this.contourStarts = new HashMap<>();
        this.contourEnds = new HashMap<>();
    }

    /**
     * Returns the level (or "z" coordinates) associated to the iso-level
     *
     * @return the level
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Returns the contours computed for this level.
     *
     * @return the contours as a list
     */
    public List<ConrecContour> getContours() {
        return new ArrayList<>(this.contours);
    }

    void addSegment(final ConrecPoint a, final ConrecPoint b) {
        ConrecContour contourA = null;
        ConrecContour contourB = null;
        boolean prependA = false;
        boolean prependB = false;

        // Find a match among the existing contours
        if (contourStarts.containsKey(a)) {
            contourA = contourStarts.get(a);
            prependA = true;
        } else if (contourEnds.containsKey(a)) {
            contourA = contourEnds.get(a);
        }

        if (contourStarts.containsKey(b)) {
            contourB = contourStarts.get(b);
            prependB = true;
        } else if (contourEnds.containsKey(b)) {
            contourB = contourEnds.get(b);
        }

        // Add the new points to the contour
        if (contourA == null && contourB == null) {
            addNewContour(a, b);
        } else if (contourA != null && contourB == null) {
            appendSegment(a, b, prependA, contourA);
        } else if (contourA == null && contourB != null) {
            appendSegment(b, a, prependB, contourB);
        } else {
            mergeContour(contourA, contourB, prependA, prependB);
        }
    }

    private void addNewContour(final ConrecPoint a, final ConrecPoint b) {
        final ConrecContour contour = new ConrecContour();
        contour.addLast(a);
        contour.addLast(b);
        contours.add(contour);
        contourStarts.put(a, contour);
        contourEnds.put(b, contour);
    }

    private void appendSegment(final ConrecPoint a, final ConrecPoint b, final boolean prepend, final ConrecContour contour) {
        if (prepend) {
            // b becomes the new start
            contour.addFirst(b);
            contourStarts.remove(a);
            contourStarts.put(b, contour);
        } else {
            // b becomes the new end
            contour.addLast(b);
            contourEnds.remove(a);
            contourEnds.put(b, contour);
        }
    }

    private void mergeContour(final ConrecContour contourA, final ConrecContour contourB, final boolean prependA, final boolean prependB) {
        contourStarts.remove(contourA.getStart());
        contourStarts.remove(contourB.getStart());
        contourEnds.remove(contourA.getEnd());
        contourEnds.remove(contourB.getEnd());

        // Close path
        if (contourA == contourB) {
            contourA.setClosed(true);
            contourA.addLast(contourA.getStart());
            return;
        }

        // Merge contours
        if (prependA && prependB) {
            // head-head
            contourA.reverseAndMerge(contourB);
            contourStarts.put(contourA.getStart(), contourA);
            contourEnds.put(contourA.getEnd(), contourA);
            contours.remove(contourB);
        } else if (prependA && !prependB) {
            // head-tail
            contourB.merge(contourA);
            contourStarts.put(contourB.getStart(), contourB);
            contourEnds.put(contourB.getEnd(), contourB);
            contours.remove(contourA);
        } else if (!prependA && prependB) {
            // tail-head
            contourA.merge(contourB);
            contourStarts.put(contourA.getStart(), contourA);
            contourEnds.put(contourA.getEnd(), contourA);
            contours.remove(contourB);
        } else {
            // tail-tail
            contourB.mergeReversed(contourA);
            contourStarts.put(contourB.getStart(), contourB);
            contourEnds.put(contourB.getEnd(), contourB);
            contours.remove(contourA);
        }
    }

    @Override
    public String toString() {
        return "Level{" +
                "z=" + z +
                ", contours=" + contours +
                '}';
    }
}
