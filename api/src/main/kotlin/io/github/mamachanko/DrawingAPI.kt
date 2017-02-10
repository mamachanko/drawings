package io.github.mamachanko

import io.github.mamachanko.instructions.GivenABlank
import io.github.mamachanko.instructions.StartBy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
class DrawingAPI(val drawingService: DrawingService) {

    @GetMapping("/api/drawing")
    fun getDrawing(@RequestParam("width", required = true) width: Double, @RequestParam("height", required = true) height: Double): ShapesResponse {

        val shapes = drawingService.getDrawing(width, height).produce()

        return ShapesResponse(shapes = shapes.map { shape ->
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

    @GetMapping("/api/v2/drawing")
    fun getDrawing2(@RequestParam("width", required = true) width: Double, @RequestParam("height", required = true) height: Double): ShapesResponse {

        val red = Color(253, 63, 51, 100)
        val blue = Color(67, 105, 178, 100)
        val yellow = Color(255, 193, 58, 100)
        val colorPalette = ColorPalette(red, blue, yellow)

        val instructions = StartBy()
                .adding().rectangles().inAGridOf(3, 5).withACollapsedMarginOf(.0)
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all().from(colorPalette)

        val drawing = GivenABlank().withWidth(width).and().withHeight(height).follow(instructions.asList())
        return ShapesResponse(shapes = drawing.shapes.map { shape ->
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
    }

}

data class ShapesResponse(val shapes: List<ShapeResource>)

data class ShapeResource(val vertices: List<VertexResource>, val color: ColorResource)

data class VertexResource(val x: Double, val y: Double)

data class ColorResource(val red: Int, val green: Int, val blue: Int, val alpha: Int)