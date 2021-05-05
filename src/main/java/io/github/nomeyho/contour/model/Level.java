package io.github.nomeyho.contour.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {

    private double z;
    private ArrayList<Contour> contours;

    public Level(final double z) {
        this.z = z;
        this.contours = new ArrayList<>();
    }

    public double getZ() {
        return this.z;
    }

    public ArrayList<Contour> getContours() {
        return this.contours;
    }

    public void addSegment(final Point a, final Point b) {
        Contour matchA = null;
        Contour matchB = null;
        boolean prependA = false;
        boolean prependB = false;
        int indexMatchA = 0;
        int indexMatchB = 0;

        //* 1. Try to find a match in the existing contours */
        for (int i = 0; i < this.contours.size(); ++i) {
            final Contour contour = this.contours.get(i);

            // No match for 'a' yet
            if (matchA == null) {
                if (contour.getStart().equal(a)) {
                    matchA = contour;
                    prependA = true;
                    indexMatchA = i;
                } else if (contour.getEnd().equal(a)) {
                    matchA = contour;
                    indexMatchA = i;
                }
            }
            // Not match for 'b' yet
            if (matchB == null) {
                if (contour.getStart().equal(b)) {
                    matchB = contour;
                    prependB = true;
                    indexMatchB = i;
                } else if (contour.getEnd().equal(b)) {
                    matchB = contour;
                    indexMatchB = i;
                }
            }
            // Both matched, not need to continue searching
            if (matchA != null && matchB != null) {
                break;
            }
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
                this.contours.add(contour);
                break;
            // a matched, b did not - thus b extends sequence ma
            case 1:
                if (prependA)
                    matchA.addFront(b);
                else
                    matchA.addBack(b);
                break;
            // b matched, a did not - thus a extends sequence mb
            case 2:
                if (prependB)
                    matchB.addFront(a);
                else
                    matchB.addBack(a);
                break;
            // both matched, can merge sequences
            case 3:
                // Contour are the same: close the path
                if (matchA == matchB) {
                    /* TODO: Only accept contour with enough points
                    if (matchA.getPoints().size() < SpectraConfiguration.MIN_CONTOUR_SIZE) {
                        this.contours.remove(indexMatchA);
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
                        this.contours.remove(indexMatchA);
                        break;
                    case 3: // head-head
                        matchA.reverse();
                    case 2: // tail-head
                        matchA.concat(matchB);
                        this.contours.remove(indexMatchB);
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
