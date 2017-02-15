package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Edge
import io.github.mamachanko.geometry.Vertex
import org.junit.Test
import kotlin.comparisons.compareBy

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
                Vertex(2.0, 6.0)
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
}

fun convexHull(vertices: Set<Vertex>): Set<Vertex> {
    return setOf(leftMost(vertices), rightMost(vertices))
}

private fun leftMost(vertices: Set<Vertex>): Vertex {
    return vertices.sortedWith(compareBy { it.x }).first()
}

private fun rightMost(vertices: Set<Vertex>): Vertex {
    return vertices.sortedWith(compareBy { it.x }).last()
}

private fun Edge.isLeftOf(vertex: Vertex): Boolean {
    return (vertex.x - a.x) * (b.y - a.y) - (vertex.y - a.y) * (b.x - a.x) < 0
}