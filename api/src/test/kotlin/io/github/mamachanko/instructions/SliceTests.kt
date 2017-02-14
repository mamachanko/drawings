package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class SliceTests {

    @Test
    fun `should return two shapes when slicing rectangle`() {
        val rectangle = listOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0))
        val drawing = Drawing().withShapes(Shape().withVertices(*rectangle.toTypedArray()))
                .follow(Slice())

        assertThat(drawing.shapes).hasSize(2)
        assertThat(drawing.vertices).hasSize(8)
        assertThat(drawing.vertices.toSet()).hasSize(6)
        assertThat(drawing.vertices.toSet()).containsAllIn(rectangle)
        val vertices1 = drawing.shapes[0].vertices
        val vertices2 = drawing.shapes[1].vertices
        assertThat(vertices1.intersect(vertices2)).hasSize(2)
    }

}