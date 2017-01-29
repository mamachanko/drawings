package io.github.mamachanko

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
        var newShapes = emptyList<Shape>().toMutableList()
        shapes.forEach { shape ->
            newShapes.addAll(shape.slice().toList())
        }
        return newShapes
    }
}