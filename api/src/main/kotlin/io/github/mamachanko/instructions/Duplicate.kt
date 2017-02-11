package io.github.mamachanko.instructions

class Duplicate(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 = state.plusShapes(state.shapes)

    fun all(): Duplicate = this

}