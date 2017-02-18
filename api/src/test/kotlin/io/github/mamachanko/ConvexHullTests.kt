package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Orientation
import io.github.mamachanko.geometry.Vertex
import io.github.mamachanko.geometry.convexHull
import io.github.mamachanko.geometry.orientationOf
import org.junit.Test

class OrientationTest {

    @Test
    fun `should return clockwise orientation for triplet of vertices`() {
        assertThat(orientationOf(Vertex(1.0, 1.0), Vertex(3.0, .0), Vertex(5.0, 3.0))).isEqualTo(Orientation.CLOCKWISE)
    }

    @Test
    fun `should return counterclockwise orientation for triplet of vertices`() {
        assertThat(orientationOf(Vertex(1.0, 1.0), Vertex(5.0, 3.0), Vertex(3.0, .0))).isEqualTo(Orientation.COUNTERCLOCKWISE)
    }

    @Test
    fun `should return colinear orientation for triplet of vertices`() {
        assertThat(orientationOf(Vertex(1.0, 1.0), Vertex(3.0, 2.0), Vertex(5.0, 3.0))).isEqualTo(Orientation.COLINEAR)
    }

}

class ConvexHullTests {

    @Test
    fun `should return convex hull of a two vertices the two vertices`() {
        assertThat(setOf(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        ).convexHull).containsExactly(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        )
    }

    @Test
    fun `should return convex hull of a triangle as the triangle itself`() {
        assertThat(setOf(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        ).convexHull).containsExactly(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        )
    }

    @Test
    fun `should return convex hull of a convex quadriliteral as the quadriliteral itself`() {
        assertThat(setOf(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0)
        ).convexHull).containsExactly(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0)
        )
    }

    @Test
    fun `should return convex hull of a concave quadriliteral as its convex triangle`() {
        assertThat(setOf(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0),
                Vertex(3.0, 3.0)
        ).convexHull).containsExactly(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0)
        )
    }

    @Test
    fun `should return convex hull of a set of vertices`() {
        assertThat(setOf(
                Vertex(1.0, 1.0),
                Vertex(2.0, 2.0),
                Vertex(3.0, 0.0),
                Vertex(2.0, .5),
                Vertex(5.0, 3.0),
                Vertex(3.0, 3.0),
                Vertex(1.0, 6.0),
                Vertex(3.0, 6.0),
                Vertex(2.0, 6.0),
                Vertex(1.5, 6.0)
        ).convexHull).containsExactly(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0),
                Vertex(3.0, 6.0)
        )
    }
}