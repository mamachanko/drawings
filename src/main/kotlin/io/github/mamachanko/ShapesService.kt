package io.github.mamachanko

import org.springframework.stereotype.Service
import java.util.*

@Service
class ShapesService {

    fun getShapesWithin(width: Double, height: Double): List<Shape> {
        return Page(
                width = width.toInt(),
                height = height.toInt(),
                layout = Layout(horizontalMargin = 25, verticalMargin = 50, tileMargin = 0),
                grid = Grid(12, 20),
                palette = GrayPalette()
        ).shapes
    }

}
