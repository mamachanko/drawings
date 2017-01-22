package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ShapeTests {

    companion object {
        val topLeft = Vertex(.0, .0)
        val topRight = Vertex(1.0, .0)
        val bottomLeft = Vertex(.0, 1.0)
        val bottomRight = Vertex(1.0, 1.0)
    }

    @Test
    fun `should return sorted vertices as open convex path`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        assertThat(shape.getSortedVertices()).containsExactly(topLeft, topRight, bottomRight, bottomLeft)
    }

    @Test
    fun `should return two shapes when sliced across two edges`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        val pieces = shape.slice()
        println(shape.getSortedVertices())
        println(pieces.first.getSortedVertices())
        println(pieces.second.getSortedVertices())
        assertThat(pieces.first.vertices.plus(pieces.second.vertices).size - 2).isEqualTo(shape.vertices.size)
        val newVertices = pieces.first.vertices.intersect(pieces.second.vertices)
        assertThat(shape.vertices).doesNotContainAnyElementsOf(newVertices)
    }

}