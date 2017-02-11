package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape

data class Drawing(val width: Double = .0, val height: Double = .0, val shapes: List<Shape> = emptyList()) {

    fun plusShapes(shapes: List<Shape>): Drawing {
        return this.copy(shapes = this.shapes.plus(shapes))
    }

    fun follow(instructions: List<Instruction>): Drawing {
        return instructions.fold(this, { state, instruction -> instruction.applyTo(state) })
    }

    fun withWidth(width: Double): Drawing {
        return this.copy(width = width)
    }

    fun withHeight(height: Double): Drawing {
        return this.copy(height = height)
    }

    fun withShapes(shapes: List<Shape>): Drawing {
        return this.copy(shapes = shapes)
    }

    fun and(): Drawing = this
}
