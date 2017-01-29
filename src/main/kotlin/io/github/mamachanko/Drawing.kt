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
//        TODO: make this idiomatic
        var newShapes = emptyList<Shape>().toMutableList()
        shapes.forEach { shape ->
            newShapes.addAll(shape.slice().toList())
        }
        return newShapes
    }
}

class DuplicateSliceAndKeepOneStrategy : Strategy {
    override fun apply(shapes: List<Shape>): List<Shape> {
//        TODO: make this idiomatic
        var newShapes = emptyList<Shape>().toMutableList()
        shapes.forEach { shape ->
            val copy1 = shape
            val copy2 = shape
            val piece1 = copy1.slice().toList()[Random().nextInt(2)]
            val piece2 = copy2.slice().toList()[Random().nextInt(2)]
            newShapes.add(piece1)
            newShapes.add(piece2)
        }
        return newShapes
    }
}