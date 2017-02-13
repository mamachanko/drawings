package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class LayoutTest {

    @Test
    fun `should place rectangle in a 1 x 1 grid as big as the given dimensions`() {
        assertThat(
                Layout(dimensions = Dimensions(100.0, 200.0), grid = Grid()).rectangleAt(GridIndex(0, 0))
        ).isEqualTo(
                Shape(setOf(Vertex(.0, .0), Vertex(100.0, .0), Vertex(100.0, 200.0), Vertex(.0, 200.0)))
        )
    }

    @Test
    fun `should place first rectangle in a 2 x 3 grid with margins`() {
        assertThat(
                Layout(dimensions = Dimensions(460.0, 380.0), grid = Grid(2, 3).withCollapsedMargin(20.0)).rectangleAt(GridIndex(0, 0))
        ).isEqualTo(
                Shape(setOf(Vertex(20.0, 20.0), Vertex(220.0, 20.0), Vertex(220.0, 120.0), Vertex(20.0, 120.0)))
        )
    }

    @Test
    fun `should place fourth rectangle in a 2 x 3 grid with margins`() {
        assertThat(
                Layout(dimensions = Dimensions(460.0, 380.0), grid = Grid(2, 3).withCollapsedMargin(20.0)).rectangleAt(GridIndex(1, 1))
        ).isEqualTo(
                Shape(setOf(Vertex(240.0, 140.0), Vertex(440.0, 140.0), Vertex(440.0, 240.0), Vertex(240.0, 240.0)))
        )
    }
}
