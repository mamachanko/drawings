package io.github.mamachanko.instructions

class StartBy {
    fun adding(): Add = Add()
}

fun GivenABlankDrawing(): Drawing {
    return Drawing()
}

fun aGridOf(columnsByRows: Pair<Int, Int>): Grid = Grid(columns = columnsByRows.first, rows = columnsByRows.second)
infix fun Int.x(that: Int): Pair<Int, Int> = Pair(this, that)