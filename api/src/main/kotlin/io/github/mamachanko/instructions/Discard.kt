package io.github.mamachanko.instructions

class Discard(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing): Drawing = state.withShapes(state.shapes.drop(1))

    fun one(): Discard {
        return this
    }

}