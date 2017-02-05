package io.github.mamachanko

import org.springframework.stereotype.Service
import java.util.*

@Service
class DrawingService(val pageTemplates: Set<PageTemplate>, val colorPalettes: Set<Palette>, val strategies: Set<Strategy>) {

    fun getDrawing(width: Double, height: Double): Drawing {
        val pageTemplate = pageTemplates.first()
        val page = Page(width = width, height = height, layout = pageTemplate.layout, grid = pageTemplate.grid)
        return Drawing(
                page = page,
                palette = colorPalettes.toList()[Random().nextInt(colorPalettes.size)],
                strategy = strategies.toList()[Random().nextInt(strategies.size)]
        )
    }
}
