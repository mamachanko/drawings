package io.github.mamachanko

import java.util.*

class Drawing(val page: Page, val palette: Palette, val strategy: Strategy) {

    fun produce(): List<Shape> {
        return strategy.apply(page.shapes).map { shape ->
            shape.color = palette.color
            shape
        }
    }
}

interface Strategy {
    fun apply(shapes: List<Shape>): List<Shape>
}

class IdempotenceStrategy : Strategy {
    override fun apply(shapes: List<Shape>): List<Shape> = shapes
}

class SliceOnceStrategy : Strategy {
    override fun apply(shapes: List<Shape>): List<Shape> {
        return shapes.map { it.slice().toList() }.flatMap { it }
    }
}

class DuplicateSliceAndKeepOneStrategy : Strategy {
    override fun apply(shapes: List<Shape>): List<Shape> {
        return shapes.map { listOf(it, it) }.flatMap { it }.map { it.slice().toList()[Random().nextInt(2)] }
    }

}