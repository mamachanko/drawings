package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex

data class Layout(val width: Double, val height: Double, val grid: Grid) {

    fun rectangleAt(gridIndex: GridIndex): Shape {
        val rectWidth = (width - grid.collapsedMargin * 2 - grid.collapsedMargin * (grid.columns - 1)) / grid.columns
        val rectHeight = (height - grid.collapsedMargin * 2 - grid.collapsedMargin * (grid.rows - 1)) / grid.rows
        val x = grid.collapsedMargin + gridIndex.column * rectWidth + grid.collapsedMargin * gridIndex.column
        val y = grid.collapsedMargin + gridIndex.row * rectHeight + grid.collapsedMargin * gridIndex.row
        return Shape().withVertices(
                Vertex(x, y),
                Vertex(x + rectWidth, y),
                Vertex(x + rectWidth, y + rectHeight),
                Vertex(x, y + rectHeight)
        )
    }
}