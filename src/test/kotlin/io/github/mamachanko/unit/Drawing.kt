package io.github.mamachanko.unit

import io.github.mamachanko.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DrawingTests {

    @Test
    fun `should produce single black shape given a page, a black color palette and idempotent strategy`() {
        val page = Page(width = 100, height = 100)
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
    fun `should produce two gray shapes given a page, a gray color palette and a slice once strategy`() {
        val page = Page(width = 100, height = 100)
        val grayPalette = GrayPalette()
        val sliceOnceStrategy = SliceOnceStrategy()

        val drawing = Drawing(page, grayPalette, sliceOnceStrategy)
        val producedShapes = drawing.produce()
        assertThat(producedShapes).hasSize(2)
        assertThat(producedShapes[0]).isNotEqualTo(producedShapes[1])
        producedShapes.map { it.color }.forEach { color ->
            assertThat(color.red == color.green && color.red == color.blue).isTrue()
        }
    }
}
