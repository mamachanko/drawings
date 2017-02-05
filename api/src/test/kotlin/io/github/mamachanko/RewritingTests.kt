package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RewritingTests {

    @Test
    fun `should produce string`() {
        val instructions = BeginningWith("z")
                .add().three().x()
                .then()
                .add().four().y()
                .then()
                .replace().every("x").with("v")
                .andSee()
        assertThat(instructions.execute()).isEqualTo("zvvvyyyy")
    }

}

class StringInstructions(val initial: String, val instructions: List<StringInstruction>) {

    fun add(): AddChar = AddChar(precedingInstructions = this)

    fun replace(): ReplaceChar = ReplaceChar(precedingInstructions = this)

    fun plus(newInstruction: StringInstruction): StringInstructions {
        return StringInstructions(
                initial = this.initial,
                instructions = this.instructions.plus(newInstruction)
        )
    }

    fun execute(): String = instructions.fold(initial, { state, instruction -> instruction.applyTo(state) })
}

fun BeginningWith(initial: String): StringInstructions = StringInstructions(initial, emptyList())


abstract class StringInstruction(val precedingInstructions: StringInstructions) {
    fun then(): StringInstructions = precedingInstructions.plus(this)

    fun andSee(): StringInstructions = this.then()

    abstract fun applyTo(state: String): String

}

class AddChar(precedingInstructions: StringInstructions) : StringInstruction(precedingInstructions) {
    private var char: String = ""
    private var count: Int = 0

    fun x(): AddChar {
        this.char = "x"
        return this
    }

    fun y(): AddChar {
        this.char = "y"
        return this
    }

    fun three(): AddChar {
        this.count = 3
        return this
    }

    fun four(): AddChar {
        this.count = 4
        return this
    }

    override fun applyTo(state: String): String = "$state${this.char.repeat(this.count)}"

}

class ReplaceChar(precedingInstructions: StringInstructions) : StringInstruction(precedingInstructions) {
    private var target: String = ""

    private var substitution: String = ""
    fun every(targetChar: String): ReplaceChar {
        this.target = targetChar
        return this
    }

    fun with(substitution: String): ReplaceChar {
        this.substitution = substitution
        return this
    }

    override fun applyTo(state: String): String = state.replace(target, substitution)

}
