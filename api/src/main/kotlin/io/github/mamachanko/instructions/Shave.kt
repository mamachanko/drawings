package io.github.mamachanko.instructions

class Shave(prior: List<Instruction>) : Slice(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.withShapes(state.shapes.map { slice(it).first() })
    }
}