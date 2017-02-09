package io.github.mamachanko.instructions

import io.github.mamachanko.BlackPalette
import io.github.mamachanko.Palette

class Colorise(prior: List<Instruction>) : Instruction(prior) {

    private var palette: Palette = BlackPalette()

    private var percent: Int = 100

    override fun applyTo(state: Drawing2): Drawing2 {
        val secondHalf = state.shapes.subList(state.shapes.size / (100 / percent), state.shapes.size)
        val firstHalf = state.shapes.subList(0, state.shapes.size / (100 / percent)).map { shape -> shape.withColour(palette.color) }
        return state.withShapes(firstHalf.plus(secondHalf))
    }

    fun from(palette: Palette): Colorise {
        this.palette = palette
        return this
    }

    fun half(): Colorise {
        this.percent = 50
        return this
    }

    fun all(): Colorise {
        this.percent = 100
        return this
    }

}