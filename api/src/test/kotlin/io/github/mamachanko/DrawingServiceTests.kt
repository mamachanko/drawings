package io.github.mamachanko.unit

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.*
import io.github.mamachanko.color.BlackPalette
import io.github.mamachanko.color.SOLID_BLACK
import io.github.mamachanko.instructions.Drawing
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.instructions.StartBy
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class DrawingServiceTests {

    @Test
    fun `should return a drawing`() {
        val width = 1230.45
        val height = 856.78

        val instructionsLibrary = setOf(StartBy().adding().a().rectangle().then().colorise().all().from(BlackPalette()).asList())

        val drawing = DrawingService(instructionsLibrary).getDrawing(width, height)

        assertThat(drawing).isEqualTo(
                Drawing().withWidth(width).withHeight(height).withShapes(listOf(Shape(
                        setOf(Vertex(.0, .0), Vertex(width, .0), Vertex(width, height), Vertex(.0, height)), color = SOLID_BLACK
                )))
        )
    }
}
