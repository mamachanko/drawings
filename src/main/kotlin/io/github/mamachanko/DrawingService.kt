package io.github.mamachanko

import org.springframework.stereotype.Service

@Service
class DrawingService(val pageTemplates: Set<PageTemplate>, val colorPalettes: Set<Palette>, val strategies: Set<Strategy>) {

    fun getDrawing(width: Double, height: Double): Drawing {
//        TODO: randomly pick palette and strategy
        val pageTemplate = pageTemplates.first()
        val page = Page(width = width.toInt(), height = height.toInt(), layout = pageTemplate.layout, grid = pageTemplate.grid)
        return Drawing(
                page = page,
                palette = colorPalettes.first(),
                strategy = strategies.first()
        )
    }
}
