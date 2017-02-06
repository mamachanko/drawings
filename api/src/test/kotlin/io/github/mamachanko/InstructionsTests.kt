package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InstructionsTests {

    @Test
    fun `should draw rectangle on page`() {
        val instructions = ToAPage()
                .withWidth(500.0).and().withHeight(900.0)
                .add().a().rectangle().fillingThePage()
                .andSee()

        assertThat(instructions.execute().shapes).hasSize(0)  // TODO: this is wrong. continue here.
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

    fun add(): Instructions {
        val precedingInstructions = Instructions(initial = this, instructions = emptyList<Instruction>())
        return Add(precedingInstructions = precedingInstructions)
    }

}

class Instructions(initial: ToAPage, instructions: List<Instruction>)

abstract class Instruction(val precedingInstructions = Instructions) {

}
