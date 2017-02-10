package io.github.mamachanko.instructions

class Add(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    // TODO: these shouldn't be mutable
    private var count: Int = 1
    private var asGrid: Boolean = false
    private var rows: Int = 1
    private var columns: Int = 1
    private var collapsedMargin: Double = .0

    override fun applyTo(state: Drawing2): Drawing2 {
        if (asGrid) {
            return state.withGrid()
        } else {
            return state.withSingles()
        }
    }

    private fun Drawing2.withGrid(): Drawing2 {
        return plusShapes(
            (0..columns - 1).map { column ->
                (0..rows - 1).map { row ->
//                    println("rectWidth = ($width - $collapsedMargin * 2 - $collapsedMargin * ($columns - 1)) / $columns")
                    val rectWidth = (width - collapsedMargin * 2 - collapsedMargin * (columns - 1)) / columns
                    val rectHeight = (height - collapsedMargin * 2 - collapsedMargin * (rows - 1)) / rows
                    val x = collapsedMargin + column * rectWidth + collapsedMargin * column
                    val y = collapsedMargin + row * rectHeight + collapsedMargin * row
//                    println("$rectWidth x $rectHeight at ($x, $y)")
                    Shape2().withVertices(
                            Vertex2(x, y),
                            Vertex2(x + rectWidth, y),
                            Vertex2(x + rectWidth, y + rectHeight),
                            Vertex2(x, y + rectHeight)
                    )
                }
            }.flatMap { it }
        )
    }

    private fun Drawing2.withSingles(): Drawing2 {
        println("adding $count shapes")
        return plusShapes((1..count).map {
            println(".")
            Shape2().withVertices(
                    Vertex2(.0, .0),
                    Vertex2(width, .0),
                    Vertex2(width, height),
                    Vertex2(.0, height)
            )
        })
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
        this.asGrid = true
        this.rows = rows
        this.columns = columns
        return this
    }

    fun withACollapsedMarginOf(collapsedMargin: Double): Add {
        this.collapsedMargin = collapsedMargin
        return this
    }

    fun one(): Add = a()
}
