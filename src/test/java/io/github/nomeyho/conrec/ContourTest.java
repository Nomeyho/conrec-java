package io.github.nomeyho.conrec;

import io.github.nomeyho.conrec.model.Contour;
import io.github.nomeyho.conrec.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContourTest {

    @Test
    void mergeTest() {
        final Contour a = a();
        final Contour b = b();

        a.merge(b);
        assertContourEquals(a, 0, 1, 2, 3, 4);
    }

    @Test
    void mergeReversedTest() {
        final Contour a = a();
        final Contour b = b();

        a.mergeReversed(b);
        assertContourEquals(a, 0, 1, 2, 4, 3);
    }

    @Test
    void reverseAndMergeTest() {
        final Contour a = a();
        final Contour b = b();

        a.reverseAndMerge(b);
        assertContourEquals(a, 2, 1, 0, 3, 4);
    }

    private Contour a() {
        final Contour a = new Contour();
        a.addLast(new Point(0, 0));
        a.addLast(new Point(1, 1));
        a.addLast(new Point(2, 2));
        return a;
    }

    private Contour b() {
        final Contour b = new Contour();
        b.addLast(new Point(3, 3));
        b.addLast(new Point(4, 4));
        return b;
    }

    private void assertContourEquals(final Contour contour, final double... points) {
        assertEquals(points.length, contour.getPoints().size());

        for (int i = 0; i < points.length; ++i) {
            assertEquals(points[i], contour.getPoints().get(i).getX());
            assertEquals(points[i], contour.getPoints().get(i).getY());
        }
    }
}