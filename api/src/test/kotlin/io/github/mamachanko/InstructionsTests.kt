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

        val drawing = GivenABlank().follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)
        assertThat(drawing.shapes.flatMap { it.vertices }).hasSize(4 * 4)

    }

}

class StartBy {
    fun adding(): Add = Add()
}

fun GivenABlank(): Drawing2 {
    return Drawing2()
}

class Drawing2(val shapes: List<Shape2> = emptyList()) {

    fun plusShapes(shapes: List<Shape2>): Drawing2 {
        return Drawing2(this.shapes.plus(shapes))
    }

    fun follow(instructions: List<Instruction>): Drawing2 {
        return instructions.fold(this, { state, instruction -> instruction.applyTo(state) })
    }

}

data class Shape2(val vertices: List<Vertex2> = emptyList()) {
    fun withVertices(vararg vertices: Vertex2): Shape2 {
        return Shape2(vertices.asList())
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
        val shape = Shape2().withVertices(Vertex2(.0, .0), Vertex2(10.0, .0), Vertex2(10.0, 10.0), Vertex2(.0, 10.0))
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
        return Drawing2(state.shapes.drop(1))
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
