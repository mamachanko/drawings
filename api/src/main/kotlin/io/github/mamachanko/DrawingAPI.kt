package io.github.mamachanko

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DrawingAPI(val drawingService: DrawingService) {

    @PostMapping("/api/drawings")
    fun createDrawing(
            @RequestParam("width", required = true) width: Double,
            @RequestParam("height", required = true) height: Double): ResponseEntity<*> {
        return ResponseEntity(drawingService.getDrawing(width, height), HttpStatus.CREATED)
    }
}
