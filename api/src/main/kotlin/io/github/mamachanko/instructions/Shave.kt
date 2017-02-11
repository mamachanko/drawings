package io.github.mamachanko.instructions

import java.util.*

class Shave(prior: List<Instruction>) : Slice(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.withShapes(state.shapes.map { slice(it).one() })
    }

    private fun <E> List<E>.one(): E {
        return get(Random().nextInt(size))
    }
}
