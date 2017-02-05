package io.github.mamachanko

data class Grid(val numberOfColumns: Int, val numberOfRows: Int)

data class Layout(val horizontalMargin: Double, val verticalMargin: Double, val tileMargin: Double)

class Page(val width: Double, val height: Double, val layout: Layout = Layout(0.0, 0.0, 0.0), val grid: Grid = Grid(1, 1)) {

    var tiles: List<Tile> = generateTiles()

    private fun generateTiles(): List<Tile> {
        return (0..grid.numberOfColumns - 1).map { column ->
            (0..grid.numberOfRows - 1).map { row ->
                Tile(x = getXForColumn(column), y = getYForRow(row), width = getTileWidth(), height = getTileHeight())
            }
        }.flatMap { it }
    }

    private fun getYForRow(row: Int): Double {
        return layout.verticalMargin + row * getTileHeight() + layout.tileMargin * row
    }

    private fun getXForColumn(column: Int): Double {
        return layout.horizontalMargin + column * getTileWidth() + layout.tileMargin * column
    }

    private fun getTileHeight(): Double {
        return (height - layout.verticalMargin * 2 - layout.tileMargin * (grid.numberOfRows - 1)) / grid.numberOfRows
    }

    private fun getTileWidth(): Double {
        return (width - layout.horizontalMargin * 2 - layout.tileMargin * (grid.numberOfColumns - 1)) / grid.numberOfColumns
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Page

        if (width != other.width) return false
        if (height != other.height) return false
        if (layout != other.layout) return false
        if (grid != other.grid) return false
        if (tiles != other.tiles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + layout.hashCode()
        result = 31 * result + grid.hashCode()
        result = 31 * result + tiles.hashCode()
        return result.toInt()
    }

    val shapes: List<Shape>
        get() {
            return tiles.map { it.shapes }.flatMap { it }
        }
}

data class Tile(val x: Double, val y: Double, val width: Double, val height: Double) {
    val shapes: List<Shape> = listOf(
            Shape(vertices = setOf(
                    Vertex(x, y),
                    Vertex(x, y + height),
                    Vertex(x + width, y),
                    Vertex(x + width, y + height)))
    )
}