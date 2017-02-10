package io.github.mamachanko.instructions

class Add(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.plusShapes((1..count).map {
            Shape2().withVertices(
                    Vertex2(.0, .0),
                    Vertex2(state.width, .0),
                    Vertex2(state.width, state.height),
                    Vertex2(.0, state.height)
            )
        })
    }

    // TODO: these shouldn't be mutable
    private var count: Int = 1
    private var asGrid: Boolean = false
    private var rows: Int = 1
    private var colums: Int = 1

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

    fun inAGridOf(rows: Int, columns: Int): Add {
        this.asGrid = true
        this.count = rows * columns
        return this
    }

    fun withACollapsedMarginOf(collapsedMargin: Double): Add = this

    fun one(): Add = a()

}