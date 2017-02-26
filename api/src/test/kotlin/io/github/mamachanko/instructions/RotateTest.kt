package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import org.junit.Test


class RotateTest {

    @Test
    fun `should rotate a square by 90 degrees`() {
        val square = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(square).follow(
                Rotate().by().ninety().degrees()
        )

        assertThat(drawing.shapes).containsExactly(square)
    }

    @Test
    fun `should rotate a square by 45 degrees twice`() {
        val square = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(square).follow(
                Rotate().by().fortyFive().degrees()
                .then()
                .rotate().by().fortyFive().degrees()
        )

        assertThat(drawing.shapes).containsExactly(square)
    }

    @Test
    fun `should rotate shape randomly`() {
        val square = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(square).follow(
                Rotate().randomly()
        )

        assertThat(drawing.shapes).hasSize(1)
        assertThat(drawing.shapes).doesNotContain(square)
    }
}
