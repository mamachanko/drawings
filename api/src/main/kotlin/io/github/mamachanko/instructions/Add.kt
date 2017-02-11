package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex

class Add(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    // TODO: these shouldn't be mutable
    private var count: Int = 0
    private var rows: Int = 1
    private var columns: Int = 1
    private var collapsedMargin: Double = .0

    override fun applyTo(state: Drawing): Drawing {

        return state.plusShapes(
                gridIndicesForShapes().map { gridIndex ->
                    rectangleAt(state.width, state.height, gridIndex.first, gridIndex.second)
                }
        )
    }

    private fun rectangleAt(width: Double, height: Double, column: Int, row: Int): Shape {
        val rectWidth = (width - collapsedMargin * 2 - collapsedMargin * (columns - 1)) / columns
        val rectHeight = (height - collapsedMargin * 2 - collapsedMargin * (rows - 1)) / rows
        val x = collapsedMargin + column * rectWidth + collapsedMargin * column
        val y = collapsedMargin + row * rectHeight + collapsedMargin * row
        return Shape().withVertices(
                Vertex(x, y),
                Vertex(x + rectWidth, y),
                Vertex(x + rectWidth, y + rectHeight),
                Vertex(x, y + rectHeight)
        )
    }

    private fun gridIndicesForShapes(): List<Pair<Int, Int>> {
        val numberOfShapes = if (count < 1) columns * rows else count
        val times = if (numberOfShapes > (columns * rows)) (numberOfShapes / (columns * rows)) else ((columns * rows) / numberOfShapes)
        val wholeGrids = times + ((columns * rows) % numberOfShapes)
        return (1..wholeGrids).map {
            (0..rows - 1).map { row ->
                (0..columns - 1).map { col ->
                    col to row
                }
            }
        }.flatMap { it }.flatMap { it }.take(numberOfShapes)
    }


    fun a(): Add {
        count = 1
        return this
    }

    fun two(): Add {
        count = 2
        return this
    }

    fun rectangle(): Add = this

    fun fillingThePage(): Add = this

    fun rectangles(): Add = this

    fun inAGridOf(columns: Int, rows: Int): Add {
        this.rows = rows
        this.columns = columns
        return this
    }

    fun withACollapsedMarginOf(collapsedMargin: Double): Add {
        this.collapsedMargin = collapsedMargin
        return this
    }

    fun one(): Add = a()

    fun three(): Add {
        this.count = 3
        return this
    }

    fun times(count: Int): Add {
        this.count = count
        return this
    }
}
