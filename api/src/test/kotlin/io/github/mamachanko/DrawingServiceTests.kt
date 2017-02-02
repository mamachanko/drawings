package io.github.mamachanko.unit

import io.github.mamachanko.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Test

class DrawingServiceTests {

    @Test
    fun `should return drawing`() {
        val width = 1230.45
        val height = 856.78

        val pageTemplates = setOf(PageTemplate(layout = Layout(50, 50, 5), grid = Grid(2, 2)))
        val colorPalettes = setOf(RandomPalette())
        val strategies = setOf(SliceOnceStrategy())

        val drawing = DrawingService(pageTemplates, colorPalettes, strategies).getDrawing(width, height)

        assertThat(drawing.page, `is`(equalTo(Page(width = width.toInt(), height = height.toInt(), layout = Layout(50, 50, 5), grid = Grid(2, 2)))))
        assertThat(drawing.palette, `is`(instanceOf(RandomPalette::class.java)))
        assertThat(drawing.strategy, `is`(instanceOf(SliceOnceStrategy::class.java)))
    }
}
