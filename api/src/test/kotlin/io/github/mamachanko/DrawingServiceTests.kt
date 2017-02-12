package io.github.mamachanko.unit

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.*
import io.github.mamachanko.color.*
import io.github.mamachanko.instructions.Drawing
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.instructions.StartBy
import io.github.mamachanko.geometry.Vertex
import io.github.mamachanko.instructions.a
import org.junit.Test

class DrawingServiceTests {

    @Test
    fun `should return a drawing with shapes`() {
        val width = 1230.45
        val height = 856.78

        val instructionsLibrary = setOf(StartBy().adding().a().rectangle().then().colorise().all().asList())
        val palettesLibrary = setOf(ColorPalette(Color(12, 34, 56, 78)))

        val drawing = DrawingService(instructionsLibrary, palettesLibrary).getDrawing(width, height)

        assertThat(drawing.shapes).containsExactly(
                Shape(setOf(Vertex(.0, .0), Vertex(width, .0), Vertex(width, height), Vertex(.0, height)), color = Color(12, 34, 56, 78))
        )
    }
}
