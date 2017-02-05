package io.github.mamachanko.unit

import io.github.mamachanko.*
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class DrawingTests {

    @Test
    fun `should produce single black shape given a page, a black color palette and idempotent strategy`() {
        val page = Page(width = 100.0, height = 100.0)
        val palette = BlackPalette()
        val idempotenceStrategy = IdempotenceStrategy()
        val drawing = Drawing(page, palette, idempotenceStrategy)

        assertThat(drawing.produce()).containsExactly(
                Shape(
                        vertices = setOf(Vertex(.0, .0), Vertex(.0, 100.0), Vertex(100.0, .0), Vertex(100.0, 100.0)),
                        color = SOLID_BLACK
                )
        )
    }

    @Test
    fun `should produce eight gray shapes given a page, a gray color palette and a slice once strategy`() {
        val page = Page(width = 100.0, height = 100.0, layout = Layout(.0, .0, .0), grid = Grid(2, 2))
        val grayPalette = GrayPalette()
        val sliceOnceStrategy = SliceOnceStrategy()

        val drawing = Drawing(page, grayPalette, sliceOnceStrategy)
        val producedShapes = drawing.produce()
        assertThat(producedShapes).hasSize(8)
        assertThat(producedShapes[0]).isNotEqualTo(producedShapes[1])
        producedShapes.map {
            assertThat(it.color.red == it.color.green && it.color.red == it.color.blue).isTrue()
        }

        val withinWidth = CoreMatchers.both(Matchers.greaterThanOrEqualTo(.0)).and(Matchers.lessThanOrEqualTo(100.0))
        val withinHeight = CoreMatchers.both(Matchers.greaterThanOrEqualTo(.0)).and(Matchers.lessThanOrEqualTo(100.0))
        producedShapes.map { it.vertices }.flatMap { it }.map { vertex ->
            Assert.assertThat(vertex.x, CoreMatchers.`is`(withinWidth))
            Assert.assertThat(vertex.y, CoreMatchers.`is`(withinHeight))
        }
    }
}
