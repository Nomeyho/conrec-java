package io.github.nomeyho.contour.msquare.model;

public class Cell {
    enum Side {LEFT, RIGHT, TOP, BOTTOM}

    private final byte index;
    private final boolean flipped;
    private final Point left;
    private final Point top;
    private final Point right;
    private final Point bottom;

    public Cell(final Point topLeft,
                final Point topRight,
                final Point bottomRight,
                final Point bottomLeft,
                final double level) {
        this.index = computeIndex(topLeft, topRight, bottomRight, bottomLeft, level);
        this.flipped = computeFlipped(topLeft, topRight, bottomRight, bottomLeft, level, index);
        this.left = new Point(
                topLeft.getX(),
                topLeft.getY() + (level - bottomLeft.getZ()) / (topLeft.getZ() - bottomLeft.getZ()),
                topLeft.getZ()
        );
        this.top = new Point(
                topLeft.getX() + (level - topLeft.getZ()) / (topRight.getZ() - topLeft.getZ()),
                topLeft.getY(),
                topLeft.getZ()
        );
        this.right = new Point(
                topLeft.getX(),
                topLeft.getY() + (level - bottomRight.getZ()) / (topRight.getZ() - bottomRight.getZ()),
                topLeft.getZ()
        );
        this.bottom = new Point(
                topLeft.getX() + (level - bottomLeft.getZ()) / (bottomRight.getZ() - bottomLeft.getZ()),
                topLeft.getY(),
                topLeft.getZ()
        );
    }

    private static byte computeIndex(final Point topLeft,
                                     final Point topRight,
                                     final Point bottomRight,
                                     final Point bottomLeft,
                                     final double level) {
        int index = (topLeft.getZ() > level ? 0 : 8);
        index |= (topRight.getZ() > level ? 0 : 4);
        index |= (bottomRight.getZ() > level ? 0 : 2);
        index |= (bottomLeft.getZ() > level ? 0 : 1);
        return (byte) index;
    }

    private static boolean computeFlipped(final Point topLeft,
                                          final Point topRight,
                                          final Point bottomRight,
                                          final Point bottomLeft,
                                          final double level,
                                          final byte index) {
        if (index == 5 || index == 10) {
            double center = (topLeft.getZ() + topRight.getZ() + bottomRight.getZ() + bottomLeft.getZ()) / 4;
            return center < level;
        } else {
            return false;
        }
    }

    private static double interpolate(final double x1, final double x2, final double y1, double y2) {
        return (y2 - y1) / (x2 - x1);
    }

    public byte getIndex() {
        return index;
    }

    public Point getPoint(final Side side) {
        switch (side) {
            case BOTTOM:
                return bottom;
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case TOP:
                return top;
            default:
                throw new IllegalArgumentException("Unknown side: " + side);
        }
    }

    public boolean isSaddle() {
        return index == 5 || index == 10;
    }

    public boolean isTrivial() {
        return index == 0 || index == 15;
    }

    public boolean isFlipped() {
        return flipped;
    }
}
