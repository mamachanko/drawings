package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class DrawingTests {

    @Test
    fun `should return its shapes' vertices`() {
        val drawing = Drawing().withShapes(Shape(setOf(Vertex(1.2, 3.4), Vertex(5.6, 7.8))))
        assertThat(drawing.vertices).containsExactly(Vertex(1.2, 3.4), Vertex(5.6, 7.8))
    }
}