package io.github.mamachanko

import org.springframework.stereotype.Service

@Service
class ShapesService {

    fun getShapesWithin(width: Double, height: Double): List<Shape> {
        val page = Page(
                width = width.toInt(),
                height = height.toInt(),
                layout = Layout(horizontalMargin = 125, verticalMargin = 50, tileMargin = 50),
                grid = Grid(2, 3)
        )
        var shapes = emptyList<Shape>().toMutableList()
        page.shapes.forEach { shape ->
            val pieces = shape.slice()
            pieces.first.color = GrayPalette().color
            pieces.second.color = GrayPalette().color
            shapes.addAll(pieces.toList())
        }
        return shapes.toList()
    }
}
