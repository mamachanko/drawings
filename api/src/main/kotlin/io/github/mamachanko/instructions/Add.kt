package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex

class Add(private var count: Int = 0, priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    private var grid: Grid = Grid()

    override fun applyTo(drawing: Drawing): Drawing {
        return drawing.plusShapes(
                gridIndicesForShapes().map { gridIndex ->
                    rectangleAt(drawing.width, drawing.height, gridIndex.first, gridIndex.second)
                }
        )
    }

    private fun rectangleAt(width: Double, height: Double, column: Int, row: Int): Shape {
        val rectWidth = (width - grid.collapsedMargin * 2 - grid.collapsedMargin * (grid.columns - 1)) / grid.columns
        val rectHeight = (height - grid.collapsedMargin * 2 - grid.collapsedMargin * (grid.rows - 1)) / grid.rows
        val x = grid.collapsedMargin + column * rectWidth + grid.collapsedMargin * column
        val y = grid.collapsedMargin + row * rectHeight + grid.collapsedMargin * row
        return Shape().withVertices(
                Vertex(x, y),
                Vertex(x + rectWidth, y),
                Vertex(x + rectWidth, y + rectHeight),
                Vertex(x, y + rectHeight)
        )
    }

    private fun gridIndicesForShapes(): List<Pair<Int, Int>> {
        val numberOfShapes = if (count < 1) grid.columns * grid.rows else count
        val times = if (numberOfShapes > (grid.columns * grid.rows)) (numberOfShapes / (grid.columns * grid.rows)) else ((grid.columns * grid.rows) / numberOfShapes)
        val wholeGrids = times + ((grid.columns * grid.rows) % numberOfShapes)
        return (1..wholeGrids).map {
            (0..grid.rows - 1).map { row ->
                (0..grid.columns - 1).map { col ->
                    col to row
                }
            }
        }.flatMap { it }.flatMap { it }.take(numberOfShapes)
    }

    fun rectangle(): Add = this

    fun rectangles(): Add = this

    fun withCount(count: Int): Add {
        this.count = count
        return this
    }

    fun withGrid(grid: Grid): Add {
        this.grid = grid
        return this
    }

    infix fun to(that: Grid): Add = this.withGrid(that)

}

fun aGridOf(columnsByRows: Pair<Int, Int>): Grid = Grid(columns = columnsByRows.first, rows = columnsByRows.second)
infix fun Int.x(that: Int): Pair<Int, Int> = Pair(this, that)

data class Grid(val columns: Int = 1, val rows: Int = 1, val collapsedMargin: Double = .0) {

    fun withCollapsedMargin(collapsedMargin: Double): Grid {
        return this.copy(collapsedMargin = collapsedMargin)
    }
}

fun Add.a(): Add = this.one()
fun Add.one(): Add = this.withCount(1)
fun Add.two(): Add = this.withCount(2)
fun Add.three(): Add = this.withCount(3)
