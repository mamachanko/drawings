package io.github.mamachanko.instructions

data class Grid(val columns: Int = 1, val rows: Int = 1, val collapsedMargin: Double = .0) {

    val size: Int = columns * rows

    fun withCollapsedMargin(collapsedMargin: Double): Grid {
        return this.copy(collapsedMargin = collapsedMargin)
    }

    fun indexOf(n: Int): GridIndex = GridIndex(columnOf(n), rowOf(n))

    private fun columnOf(n: Int) = n % columns

    private fun rowOf(n: Int) = Math.floorDiv(n, columns) % rows
}

data class GridIndex(val column: Int, val row: Int)