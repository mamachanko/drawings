package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex

class Add(private var count: Int = 0, priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    private var grid: Grid = Grid()

    private val numberOfRectangles: Int
        get() = if (count < 1) grid.size else count

    override fun applyTo(drawing: Drawing): Drawing {
        return drawing.plusShapes(
                (0..numberOfRectangles - 1).map { grid.indexOf(it) }.map { gridIndex ->
                    rectangleAt(drawing.width, drawing.height, gridIndex.column, gridIndex.row)
                })
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

fun Add.a(): Add = this.one()
fun Add.one(): Add = this.withCount(1)
fun Add.two(): Add = this.withCount(2)
fun Add.three(): Add = this.withCount(3)
