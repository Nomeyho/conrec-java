package io.github.nomeyho.conrec.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConrecContourTest {

    @Test
    void mergeTest() {
        final ConrecContour a = a();
        final ConrecContour b = b();

        a.merge(b);
        assertContourEquals(a, 0, 1, 2, 3, 4);
    }

    @Test
    void mergeReversedTest() {
        final ConrecContour a = a();
        final ConrecContour b = b();

        a.mergeReversed(b);
        assertContourEquals(a, 0, 1, 2, 4, 3);
    }

    @Test
    void reverseAndMergeTest() {
        final ConrecContour a = a();
        final ConrecContour b = b();

        a.reverseAndMerge(b);
        assertContourEquals(a, 2, 1, 0, 3, 4);
    }

    private ConrecContour a() {
        final ConrecContour a = new ConrecContour();
        a.addLast(new ConrecPoint(0, 0));
        a.addLast(new ConrecPoint(1, 1));
        a.addLast(new ConrecPoint(2, 2));
        return a;
    }

    private ConrecContour b() {
        final ConrecContour b = new ConrecContour();
        b.addLast(new ConrecPoint(3, 3));
        b.addLast(new ConrecPoint(4, 4));
        return b;
    }

    private void assertContourEquals(final ConrecContour contour, final double... points) {
        assertEquals(points.length, contour.getPoints().size());

        for (int i = 0; i < points.length; ++i) {
            assertEquals(points[i], contour.getPoints().get(i).getX());
            assertEquals(points[i], contour.getPoints().get(i).getY());
        }
    }
}