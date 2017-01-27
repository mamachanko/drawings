package io.github.mamachanko

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
class Api(val shapesService: ShapesService) {

    @GetMapping("/api/shapes")
    fun shapes(@RequestParam("width", required = true) width: Double, @RequestParam("height", required = true) height: Double): ShapesResponse {

        return ShapesResponse(shapes = shapesService.getShapes(width, height).map { shape ->
            ShapeResource(
                    vertices = shape.getSortedVertices().map { vertex ->
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
    }
}

data class ShapesResponse(val shapes: List<ShapeResource>)

data class ShapeResource(val vertices: List<VertexResource>, val color: ColorResource)

data class VertexResource(val x: Double, val y: Double)

data class ColorResource(val red: Int, val green: Int, val blue: Int, val alpha: Int)