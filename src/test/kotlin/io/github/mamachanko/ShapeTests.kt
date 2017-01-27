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
        val newVertices = pieces.first.vertices.intersect(pieces.second.vertices)
        assertThat(shape.vertices).doesNotContainAnyElementsOf(newVertices)
    }

    @Test
    fun `should return solid black as default color`() {
        assertThat(Shape().color).isEqualTo(Color(0, 0, 0, 100))
    }

    @Test
    fun `should return color if given`() {
        val shape = Shape(
                vertices = setOf(topLeft, topRight, bottomLeft, bottomRight),
                color = Color(125, 67, 32, 25)
        )
        assertThat(shape.color).isEqualTo(Color(125, 67, 32, 25))
    }
}