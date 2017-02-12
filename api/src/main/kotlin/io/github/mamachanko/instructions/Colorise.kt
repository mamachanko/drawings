package io.github.mamachanko.instructions

import io.github.mamachanko.color.BlackPalette
import io.github.mamachanko.color.Palette

class Colorise(prior: List<Instruction>) : Instruction(prior) {

    private var percent: Int = 100

    override fun applyTo(drawing: Drawing): Drawing {
        val palette = drawing.palette
        val secondHalf = drawing.shapes.subList(drawing.shapes.size / (100 / percent), drawing.shapes.size)
        val firstHalf = drawing.shapes.subList(0, drawing.shapes.size / (100 / percent)).map { shape -> shape.withColour(palette.color) }
        return drawing.withShapes(firstHalf.plus(secondHalf))
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