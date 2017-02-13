package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import java.util.*

data class Dimensions(val width: Double, val height: Double)

data class Layout(val dimensions: Dimensions, val grid: Grid) {

    fun randomRectangle(): Shape {
        return rectangleAt(GridIndex(Random().nextInt(grid.columns), Random().nextInt(grid.rows)))
    }

    fun rectangleAt(gridIndex: GridIndex): Shape {
        val (width, height) = rectangleDimensions
        val x = xAt(gridIndex)
        val y = yAt(gridIndex)
        return Shape().withVertices(
                Vertex(x, y),
                Vertex(x + width, y),
                Vertex(x + width, y + height),
                Vertex(x, y + height)
        )
    }

    private fun xAt(gridIndex: GridIndex): Double {
        return grid.collapsedMargin + gridIndex.column * rectangleDimensions.first + grid.collapsedMargin * gridIndex.column
    }

    private fun yAt(gridIndex: GridIndex): Double {
        return grid.collapsedMargin + gridIndex.row * rectangleDimensions.second + grid.collapsedMargin * gridIndex.row
    }

    private val rectangleDimensions = Pair(
            (dimensions.width - grid.collapsedMargin * 2 - grid.collapsedMargin * (grid.columns - 1)) / grid.columns,
            (dimensions.height - grid.collapsedMargin * 2 - grid.collapsedMargin * (grid.rows - 1)) / grid.rows
    )
}