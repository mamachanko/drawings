package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PageTests {

    @Test
    fun `should have tiles space according to layout and grid`() {
        val page = Page(
                width = 1000,
                height = 800,
                layout = Layout(horizontalMargin = 50, verticalMargin = 125, tileMargin = 10),
                grid = Grid(2, 3)
        )
        assertThat(page.tiles).containsExactlyInAnyOrder(
                Tile(50, 125, 445, 176),
                Tile(505, 125, 445, 176),
                Tile(50, 311, 445, 176),
                Tile(505, 311, 445, 176),
                Tile(50, 497, 445, 176),
                Tile(505, 497, 445, 176)
        )
    }

    @Test
    fun `should have one shape in each tile composed of its corners by default`() {
        val page = Page(
                width = 100,
                height = 100,
                layout = Layout(horizontalMargin = 0, verticalMargin = 0, tileMargin = 0),
                grid = Grid(1, 1)
        )
        assertThat(page.tiles.first().shapes).containsExactly(
                Shape(setOf(Vertex(.0, .0), Vertex(.0, 100.0), Vertex(100.0, .0), Vertex(100.0, 100.0)))
        )
    }
}

