package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InstructionsTests {

    @Test
    fun `should create drawing from list of instructions`() {
        val instructions = StartBy()
                .adding().a().rectangle().fillingThePage()
                .then()
                .add().two().rectangles().fillingThePage()
                .then()
                .discard().one()
                .then()
                .duplicate().all()
                .then()
                .colorise().half().from(GrayPalette())

        val drawing = GivenABlank()
                .withWidth(600.0).and().withHeight(800.0)
                .follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)
        drawing.shapes.map {
            assertThat(it.vertices).containsExactly(
                    Vertex2(.0, .0), Vertex2(600.0, .0), Vertex2(600.0, 800.0), Vertex2(.0, 800.0)
            )
        }
        assertThat(drawing.shapes.filter { it.color.equals(SOLID_BLACK) }).hasSize(2)
        assertThat(drawing.shapes.filter { !it.color.equals(SOLID_BLACK) }).hasSize(2)
    }

    @Test
    fun `should be able to slice shapes`() {
        val instructions = Add().one().rectangle().then().slice().randomly()

        val drawing = GivenABlank().withWidth(100.0).and().withHeight(100.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(2)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(8)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(6)
        drawing.shapes.map {
            assertThat(it.vertices).containsAllOf(Vertex2(.0, .0), Vertex2(100.0, .0), Vertex2(100.0, 100.0), Vertex2(.0, 100.0))
        }
        val vertices1 = drawing.shapes[0].vertices
        val vertices2 = drawing.shapes[1].vertices
        assertThat(vertices1.intersect(vertices2)).hasSize(2)
    }

    @Test
    fun `should create the baseline Kelleybert drawing from list of instructions`() {
        val color1 = Color(10, 20, 30, SOLID)
        val color2 = Color(40, 50, 60, SOLID)
        val instructions = StartBy()
                .adding().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(20.0)
                .then()
                .duplicate().all()
                .then()
                .slice().all().randomly().butOnlyKeepOnePiece()
                .then()
                .colorise().all().from(ColorPalette(color1, color2))

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(12)
        assertThat(drawing.shapes.map { it.color }.toSet()).doesNotContain(SOLID_BLACK)
        assertThat(drawing.shapes.map { it.color }.toSet()).containsExactly(color1, color2)
    }

}

class StartBy {
    fun adding(): Add = Add()
}

fun GivenABlank(): Drawing2 {
    return Drawing2()
}

data class Drawing2(val width: Double = .0, val height: Double = .0, val shapes: List<Shape2> = emptyList()) {

    fun plusShapes(shapes: List<Shape2>): Drawing2 {
        return this.copy(shapes = this.shapes.plus(shapes))
    }

    fun follow(instructions: List<Instruction>): Drawing2 {
        return instructions.fold(this, { state, instruction -> instruction.applyTo(state) })
    }

    fun withWidth(width: Double): Drawing2 {
        return this.copy(width = width)
    }

    fun withHeight(height: Double): Drawing2 {
        return this.copy(height = height)
    }

    fun withShapes(shapes: List<Shape2>): Drawing2 {
        return this.copy(shapes = shapes)
    }

    fun and(): Drawing2 {
        return this
    }

}

data class Shape2(val vertices: List<Vertex2> = emptyList(), val color: Color = SOLID_BLACK) {

    fun withVertices(vararg vertices: Vertex2): Shape2 {
        return this.copy(vertices = vertices.asList())
    }

    fun withColour(color: Color): Shape2 {
        return this.copy(color = color)
    }
}

data class Vertex2(val x: Double, val y: Double)

abstract class Instruction(val prior: List<Instruction> = emptyList()) {

    fun add(): Add {
        return Add(prior.plus(this))
    }

    fun discard(): Discard {
        return Discard(prior.plus(this))
    }

    fun duplicate(): Duplicate {
        return Duplicate(prior.plus(this))
    }

    fun colorise(): Colorise {
        return Colorise(prior.plus(this))
    }

    fun slice(): Slice {
        return Slice(prior.plus(this))
    }

    fun then(): Instruction {
        return this
    }

    fun asList(): List<Instruction> {
        return prior.plus(this)
    }

    abstract fun applyTo(state: Drawing2): Drawing2
}

class Add(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        val width = state.width
        val height = state.height
        val shape = Shape2().withVertices(
                Vertex2(.0, .0),
                Vertex2(width, .0),
                Vertex2(width, height),
                Vertex2(.0, height)
        )
        return state.plusShapes((1..count).map { shape })
    }

    private var count: Int = 0

    fun a(): Add {
        count = 1
        return this
    }

    fun two(): Add {
        count = 2
        return this
    }

    fun rectangle(): Add {
        return this
    }

    fun fillingThePage(): Add {
        return this
    }

    fun rectangles(): Add {
        return this
    }

    fun inAGridOf(rows: Int, columns: Int): Add {
        this.count = rows * columns
        return this
    }

    fun withACollapsedMarginOf(collapsedMargin: Double): Add {
        return this
    }

    fun one(): Add {
        return a()
    }

}

class Discard(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.withShapes(state.shapes.drop(1))
    }

    fun one(): Discard {
        return this
    }

}

class Duplicate(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.plusShapes(state.shapes)
    }

    fun all(): Duplicate {
        return this
    }

}

class Colorise(prior: List<Instruction>) : Instruction(prior) {

    private var palette: Palette = BlackPalette()

    private var percent: Int = 100

    override fun applyTo(state: Drawing2): Drawing2 {
        val secondHalf = state.shapes.subList(state.shapes.size / (100 / percent), state.shapes.size)
        val firstHalf = state.shapes.subList(0, state.shapes.size / (100 / percent)).map { shape -> shape.withColour(palette.color) }
        return state.withShapes(firstHalf.plus(secondHalf))
    }

    fun from(palette: Palette): Colorise {
        this.palette = palette
        return this
    }

    fun half(): Colorise {
        this.percent = 50
        return this
    }

    fun all(): Colorise {
        this.percent = 100
        return this
    }

}

class Slice(prior: List<Instruction>) : Instruction(prior) {
    override fun applyTo(state: Drawing2): Drawing2 {
        return state.withShapes(state.shapes.map { slice(it) }.flatMap { it })
    }

    private fun slice(shape: Shape2): List<Shape2> {
        return listOf(shape, shape)
    }

    fun all(): Slice {
        return this
    }

    fun randomly(): Slice {
        return this
    }

    fun butOnlyKeepOnePiece(): Slice {
        return this
    }
}