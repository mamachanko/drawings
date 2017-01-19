package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

val topLeft = Vertex(0, 0)
val topRight = Vertex(1, 0)
val bottomLeft = Vertex(0, 1)
val bottomRight = Vertex(1, 1)

class ShapeTest {

    @Test
    fun `should order vertices as convex`() {
        val shape = Shape(topLeft, topRight, bottomLeft, bottomRight)
        assertThat(shape.getSortedVertices()).containsExactly(topLeft, topRight, bottomLeft, bottomRight)
    }

    @Test
    fun `should be sliceable in two`() {
        val shape = Shape(topLeft, topRight, bottomLeft, bottomLeft)
        val pieces = shape.slice()
    }
}

class Shape(vararg val vertices: Vertex) {

    fun slice(): Pair<Shape, Shape> {
        return Pair(Shape(), Shape())
    }

    fun getSortedVertices(): Array<Vertex> {
        return vertices as Array<Vertex>
    }
}

data class Vertex(val x: Int, val y: Int)