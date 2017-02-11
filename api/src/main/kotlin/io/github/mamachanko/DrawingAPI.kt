package io.github.mamachanko

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DrawingAPI(val drawingService: DrawingService) {

    @PostMapping("/api/drawings")
    fun createDrawing(@RequestParam("width", required = true) width: Double, @RequestParam("height", required = true) height: Double): ResponseEntity<*> {
        val drawing = drawingService.getDrawing(width, height)

        val shapesResponse = ShapesResponse(shapes = drawing.shapes.map { shape ->
            ShapeResource(
                    vertices = shape.path.map { vertex ->
                        VertexResource(x = vertex.x, y = vertex.y)
                    },
                    color = ColorResource(
                            red = shape.color.red,
                            green = shape.color.green,
                            blue = shape.color.blue,
                            alpha = shape.color.alpha
                    )
            )
        })

        return ResponseEntity(shapesResponse, HttpStatus.CREATED)
    }
}

data class ShapesResponse(val shapes: List<ShapeResource>)

data class ShapeResource(val vertices: List<VertexResource>, val color: ColorResource)

data class VertexResource(val x: Double, val y: Double)

data class ColorResource(val red: Int, val green: Int, val blue: Int, val alpha: Int)