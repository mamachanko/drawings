package io.github.mamachanko.instructions

class Discard(priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    override fun applyTo(drawing: Drawing): Drawing = drawing.withShapes(drawing.shapes.drop(1))

    fun one(): Discard {
        return this
    }

}