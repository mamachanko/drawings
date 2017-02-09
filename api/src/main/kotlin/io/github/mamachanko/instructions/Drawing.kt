package io.github.mamachanko.instructions

data class Drawing2(val width: Double = .0, val height: Double = .0, val shapes: List<Shape2> = emptyList()) {

    fun plusShapes(shapes: List<Shape2>): Drawing2 {
        return this.copy(shapes = this.shapes.plus(shapes))
    }

    fun follow(instructions: List<Instruction>): Drawing2 {
        return instructions.fold(this, { state, instruction -> instruction.applyTo(state) })
    }

    fun withWidth(width: Double): Drawing2 {
        return this.copy(width = width)
    }

    fun withHeight(height: Double): Drawing2 {
        return this.copy(height = height)
    }

    fun withShapes(shapes: List<Shape2>): Drawing2 {
        return this.copy(shapes = shapes)
    }

    fun and(): Drawing2 = this
}
