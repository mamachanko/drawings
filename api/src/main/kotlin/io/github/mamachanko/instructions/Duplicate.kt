package io.github.mamachanko.instructions

class Duplicate(priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    override fun applyTo(drawing: Drawing): Drawing = drawing.plusShapes(drawing.shapes)

    fun all(): Duplicate = this

}