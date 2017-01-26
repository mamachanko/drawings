package io.github.mamachanko

import org.springframework.stereotype.Service
import java.util.*

@Service
class ShapesService {

    fun getShapes(width: Double, height: Double): List<Shape> {
        val random = Random()
        return Page(
                width = width.toInt(),
                height = height.toInt(),
                layout = Layout(horizontalMargin = 25, verticalMargin = 50, tileMargin = 10),
                grid = Grid(random.nextInt(10) + 1, random.nextInt(10) + 1),
                palette = RandomPalette()
        ).shapes
    }

}
