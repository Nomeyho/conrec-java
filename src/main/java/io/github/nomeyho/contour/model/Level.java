package io.github.nomeyho.contour.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Level implements Serializable {

    private double z;
    private LinkedHashSet<Contour> contours;
    private Map<Point, Contour> openContourStarts;
    private Map<Point, Contour> openContourEnds;

    public Level(final double z) {
        this.z = z;
        this.contours = new LinkedHashSet<>();
        this.openContourStarts = new HashMap<>();
        this.openContourEnds = new HashMap<>();
    }

    public double getZ() {
        return this.z;
    }

    public List<Contour> getContours() {
        return new ArrayList<>(this.contours);
    }

    public void addSegment(final Point a, final Point b) {
        Contour matchA = null;
        Contour matchB = null;
        boolean prependA = false;
        boolean prependB = false;

        //* 1. Try to find a match in the existing contours */
        if (openContourStarts.containsKey(a)) {
            matchA = openContourStarts.get(a);
            prependA = true;
        } else if (openContourEnds.containsKey(a)) {
            matchA = openContourEnds.get(a);
        }

        if (openContourStarts.containsKey(b)) {
            matchB = openContourStarts.get(b);
            prependB = true;
        } else if (openContourEnds.containsKey(b)) {
            matchB = openContourEnds.get(b);
        }

        /* 2. Add the new points to the contour */
        final int c = ((matchA != null) ? 1 : 0) | ((matchB != null) ? 2 : 0);
        final Contour contour;

        switch (c) {
            // Both are unmatched, add as new sequence
            case 0:
                contour = new Contour();
                contour.addBack(a);
                contour.addBack(b);
                contours.add(contour);
                openContourStarts.put(a, contour);
                openContourEnds.put(b, contour);
                break;
            // a matched, b did not - thus b extends sequence ma
            case 1:
                if (prependA) {
                    // b becomes the new start
                    matchA.addFront(b);
                    openContourStarts.remove(a);
                    openContourStarts.put(b, matchA);
                } else {
                    // b becomes the new end
                    matchA.addBack(b);
                    openContourEnds.remove(a);
                    openContourEnds.put(b, matchA);
                }
                break;
            // b matched, a did not - thus a extends sequence mb
            case 2:
                if (prependB) {
                    // a becomes the new start
                    matchB.addFront(a);
                    openContourStarts.remove(b);
                    openContourStarts.put(a, matchB);
                } else {
                    matchB.addBack(a);
                    openContourEnds.remove(b);
                    openContourEnds.put(a, matchB);
                }
                break;
            // both matched, can merge sequences
            case 3:
                openContourStarts.remove(matchA.getStart());
                openContourStarts.remove(matchB.getStart());
                openContourEnds.remove(matchA.getEnd());
                openContourEnds.remove(matchB.getEnd());
                
                // Contour are the same: close the path
                if (matchA == matchB) {
                    /* TODO: Only accept contour with enough points
                    if (matchA.getPoints().size() < SpectraConfiguration.MIN_CONTOUR_SIZE) {
                        contours.remove(indexMatchA);
                    }
                    */
                    matchA.setClosed(true);
                    matchA.addBack(matchA.getStart());
                    break;
                }

                /*
                 * There are 4 ways the sequence pair can be joined. The current setting of prependA and
                 * prependB will tell us which type of join is needed. For head/head and tail/tail joins
                 * one sequence needs to be reversed
                 */
                switch ((prependA ? 1 : 0) | (prependB ? 2 : 0)) {
                    case 0: // tail-tail
                        matchA.reverse();
                    case 1: // head-tail
                        matchB.concat(matchA);
                        openContourStarts.put(matchB.getStart(), matchB);
                        openContourEnds.put(matchB.getEnd(), matchB);
                        contours.remove(matchA);
                        break;
                    case 3: // head-head
                        matchA.reverse();
                    case 2: // tail-head
                        matchA.concat(matchB);
                        openContourStarts.put(matchA.getStart(), matchA);
                        openContourEnds.put(matchA.getEnd(), matchA);
                        contours.remove(matchB);
                        break;
                }
                break;
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
