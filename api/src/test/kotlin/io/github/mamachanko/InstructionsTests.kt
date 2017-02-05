package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InstructionsTests {

    @Test
    fun `should draw rectangle on page`() {
        val instructions = ToAPage()
                .withWidth(500.0).and().withHeight(900.0)
                .add().a().rectangle().fillingThePage()
                .then()
                .add().two().rectangles().fillingThePage()
                .then()
                .discard().one()
                .then()
                .duplicate().all()
                .andSee()

        assertThat(instructions.execute().shapes).hasSize(4)
    }

}

class ToAPage(var width: Double = .0, var height: Double = .0) {

    fun withWidth(width: Double): ToAPage {
        this.width = width
        return this
    }

    fun and(): ToAPage {
        return this
    }

    fun withHeight(height: Double): ToAPage {
        this.height = height
        return this
    }

    fun add(): Add {
        val emptyDrawing = ADrawing()
        val precedingInstructions = Instructions(emptyDrawing, emptyList<Instruction>())
        return Add(precedingInstructions)
    }

}

class Instructions(val initial: ADrawing, val instructions: List<Instruction>) {

    fun plus(newInstruction: Instruction): Instructions {
        return Instructions(initial, instructions.plus(newInstruction))
    }

    fun execute(): ADrawing {
        return instructions.fold(initial, {state, instruction -> instruction.applyTo(state)})
    }

    fun add(): Add {
        return Add(this)
    }

    fun discard(): Discard {
        return Discard(this)
    }

    fun duplicate(): Duplicate {
        return Duplicate(this)
    }
}

abstract class Instruction(val precedingInstructions: Instructions) {

    fun andSee(): Instructions {
        return then()
    }

    fun then(): Instructions {
        return precedingInstructions.plus(this)
    }

    abstract fun applyTo(state: ADrawing): ADrawing

}
class ADrawing(val shapes: List<AShape> = emptyList()) {

    fun plusShapes(shapes: List<AShape>): ADrawing {
        return ADrawing(this.shapes.plus(shapes))
    }

}

class AShape

class Add(precedingInstructions: Instructions) : Instruction(precedingInstructions) {

    override fun applyTo(state: ADrawing): ADrawing {
        if (count == 1) {
            return state.plusShapes(listOf(AShape()))
        }
        return state.plusShapes(listOf(AShape(), AShape()))
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

class Discard(precedingInstructions: Instructions) : Instruction(precedingInstructions) {
    override fun applyTo(state: ADrawing): ADrawing {
        return ADrawing(state.shapes.drop(1))
    }

    fun one(): Discard {
        return this
    }

}
