package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PageTests {

    @Test
    fun `should have tiles space according to layout and grid`() {
        val grid = Grid(2, 3)
        val horizontalMargin = 50
        val verticalMargin = 125
        val tileMargin = 10
        val layout = Layout(horizontalMargin, verticalMargin, tileMargin)
        val width = 1000
        val height = 800
        val page = Page(width, height, layout, grid)

        assertThat(page.tiles).containsExactlyInAnyOrder(
                Tile(50, 125, 445, 176),
                Tile(505, 125, 445, 176),
                Tile(50, 311, 445, 176),
                Tile(505, 311, 445, 176),
                Tile(50, 497, 445, 176),
                Tile(505, 497, 445, 176)
        )
    }
}

