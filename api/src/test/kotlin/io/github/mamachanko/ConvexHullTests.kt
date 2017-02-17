package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Vertex
import org.junit.Test
import kotlin.comparisons.compareBy

enum class Orientation {
    CLOCKWISE, COUNTERCLOCKWISE, COLINEAR
}

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
    fun `should return convex hull of a set of vertices`() {
        assertThat(convexHull(setOf(
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
        ))).containsExactly(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0),
                Vertex(3.0, 6.0)
        )
    }

    @Test
    fun `should return convex hull of a triangle as the triangle itself`() {
        assertThat(convexHull(setOf(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        ))).containsExactly(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        )
    }

    @Test
    fun `should return convex hull of a convex quadriliteral as the quadriliteral itself`() {
        assertThat(convexHull(setOf(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0)
        ))).containsExactly(
                Vertex(1.0, 1.0),
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0)
        )
    }

    @Test
    fun `should return convex hull of a concave quadriliteral as its convex triangle`() {
        assertThat(convexHull(setOf(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0),
                Vertex(3.0, 3.0)
        ))).containsExactly(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0),
                Vertex(1.0, 6.0)
        )
    }

    @Test
    fun `should return convex hull of a two vertices the two vertices`() {
        assertThat(convexHull(setOf(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        ))).containsExactly(
                Vertex(3.0, 0.0),
                Vertex(5.0, 3.0)
        )
    }
}

fun convexHull(vertices: Set<Vertex>): Set<Vertex> {
    val start = leftMost(vertices)
    val convexHull = mutableSetOf(start)
    var next = start
    while (true) {
        next = findNextOnConvexHull(vertices.minus(next), next)
        if (next == start) {
            break
        } else {
            convexHull.add(next)
        }
    }
    return convexHull
}

fun findNextOnConvexHull(vertices: Set<Vertex>, current: Vertex): Vertex {
    return vertices.find {
        vertices.minus(it).filter { x ->
            orientationOf(current, it, x) != Orientation.COUNTERCLOCKWISE
        }.size == vertices.size - 1
    }!!
}

fun leftMost(vertices: Set<Vertex>): Vertex {
    return vertices.sortedWith(compareBy { it.x }).first()
}

fun orientationOf(a: Vertex, b: Vertex, c: Vertex): Orientation {
    val orientation = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y)
    return when {
        orientation < 0 -> Orientation.CLOCKWISE
        orientation > 0 -> Orientation.COUNTERCLOCKWISE
        else -> Orientation.COLINEAR
    }
}