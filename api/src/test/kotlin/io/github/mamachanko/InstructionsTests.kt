package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InstructionsTests {

    @Test
    fun `should create instructions first and execute on initial later`() {
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
        assertThat(drawing.shapes.flatMap { it.vertices }).hasSize(4 * 4)
        drawing.shapes.map {
            assertThat(it.vertices).containsExactly(Vertex2(.0, .0), Vertex2(600.0, .0), Vertex2(600.0, 800.0), Vertex2(.0, 800.0))
        }
        assertThat(drawing.shapes.filter { !it.color.equals(SOLID_BLACK) }).hasSize(2)
        assertThat(drawing.shapes.filter { it.color.equals(SOLID_BLACK) }).hasSize(2)
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

    override fun applyTo(state: Drawing2): Drawing2 {
        val shapes = state.shapes.map { shape -> shape.withColour(palette.color) }
        return state.withShapes(shapes)
    }

    fun all(): Colorise {
        return this
    }

    fun from(palette: Palette): Colorise {
        this.palette = palette
        return this
    }

    fun half(): Colorise {
        return this
    }

}
