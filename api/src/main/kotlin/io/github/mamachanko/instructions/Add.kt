package io.github.mamachanko.instructions

class Add(private var count: Int = 0, priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    private var random: Boolean = false

    private var grid: Grid = Grid()

    private val Drawing.layout: Layout
        get() = Layout(Dimensions(width, height), grid)

    private val numberOfRectangles: Int
        get() = if (count < 1) grid.size else count

    override fun applyTo(drawing: Drawing): Drawing {
        return drawing.plusShapes((0..numberOfRectangles - 1).map { drawing.layout.rectangleAt(grid.indexOf(it)) })
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
    fun randomly(): Add {
        this.random = true
        return this
    }
}

fun Add.a(): Add = this.one()
fun Add.one(): Add = this.withCount(1)
fun Add.two(): Add = this.withCount(2)
fun Add.three(): Add = this.withCount(3)
