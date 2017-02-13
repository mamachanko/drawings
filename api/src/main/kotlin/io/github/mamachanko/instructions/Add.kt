package io.github.mamachanko.instructions

class Add(private var count: Int = 0, priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    private var grid: Grid = Grid()

    private val numberOfRectangles: Int
        get() = if (count < 1) grid.size else count

    override fun applyTo(drawing: Drawing): Drawing {
        val layout = Layout(drawing.width, drawing.height, grid)
        return drawing.plusShapes((0..numberOfRectangles - 1).map { layout.rectangleAt(grid.indexOf(it)) })
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
