package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PageTests {

    @Test
    fun `should assume default values except for width and height`() {
        val page = Page(width = 100, height = 100)
        assertThat(page.width).isEqualTo(100)
        assertThat(page.height).isEqualTo(100)
        assertThat(page.layout.horizontalMargin).isEqualTo(0)
        assertThat(page.layout.verticalMargin).isEqualTo(0)
        assertThat(page.layout.horizontalMargin).isEqualTo(0)
        assertThat(page.grid.numberOfRows).isEqualTo(1)
        assertThat(page.grid.numberOfColumns).isEqualTo(1)
        assertThat(page.palette).isInstanceOf(BlackPalette::class.java)
    }

    @Test
    fun `should have tiles space according to layout and grid`() {
        val page = Page(
                width = 1000,
                height = 800,
                layout = Layout(horizontalMargin = 50, verticalMargin = 125, tileMargin = 10),
                grid = Grid(2, 3)
        )
        assertThat(page.tiles).containsExactlyInAnyOrder(
                Tile(50, 125, 445, 176, page.palette),
                Tile(505, 125, 445, 176, page.palette),
                Tile(50, 311, 445, 176, page.palette),
                Tile(505, 311, 445, 176, page.palette),
                Tile(50, 497, 445, 176, page.palette),
                Tile(505, 497, 445, 176, page.palette)
        )
    }

    @Test
    fun `should have one black shape in each tile composed of its corners by default`() {
        val page = Page(
                width = 100,
                height = 100,
                layout = Layout(horizontalMargin = 0, verticalMargin = 0, tileMargin = 0),
                grid = Grid(1, 1)
        )

        assertThat(page.tiles.first().shapes).hasSize(1)
        val shape = page.tiles.first().shapes.first()
        assertThat(shape.vertices).containsExactly(
                Vertex(.0, .0), Vertex(.0, 100.0), Vertex(100.0, .0), Vertex(100.0, 100.0)
        )
        assertThat(shape.color).isEqualTo(SOLID_BLACK)
    }

    @Test
    fun `should return all its tiles' shapes`() {
        assertThat(Page(width = 100, height = 100).shapes).hasSize(1)
    }

    @Test
    fun `should apply color to shapes given palette`() {
        val color = Color(180, 90, 0, .25)
        val page = Page(height = 100, width = 100, palette = ColorPalette(color))
        assertThat(page.tiles.first().shapes.first().color).isEqualTo(color)
    }
}

