package io.github.mamachanko.instructions

import io.github.mamachanko.color.BlackPalette
import io.github.mamachanko.color.Palette
import io.github.mamachanko.geometry.Shape

data class Drawing(val width: Double = .0, val height: Double = .0, val palette: Palette = BlackPalette(), val shapes: List<Shape> = emptyList()) {

    fun plusShapes(shapes: List<Shape>): Drawing = this.copy(shapes = this.shapes.plus(shapes))

    fun follow(instructions: List<Instruction>): Drawing {
        return instructions.fold(this, { state, instruction -> instruction.applyTo(state) })
    }

    fun withWidth(width: Double): Drawing = this.copy(width = width)

    fun withHeight(height: Double): Drawing = this.copy(height = height)

    fun withShapes(shapes: List<Shape>): Drawing = this.copy(shapes = shapes)

    fun withPalette(palette: Palette): Drawing = this.copy(palette = palette)

    fun and(): Drawing = this
}
