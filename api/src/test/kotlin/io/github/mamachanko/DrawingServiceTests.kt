package io.github.mamachanko.unit

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.*
import io.github.mamachanko.instructions.Drawing2
import io.github.mamachanko.instructions.Shape2
import io.github.mamachanko.instructions.StartBy
import io.github.mamachanko.instructions.Vertex2
import org.junit.Test

class DrawingServiceTests {

    @Test
    fun `should return a drawing2`() {
        val width = 1230.45
        val height = 856.78

        val instructionsLibrary = setOf(StartBy().adding().a().rectangle().then().colorise().all().from(BlackPalette()).asList())

        val drawing = DrawingService(instructionsLibrary).getDrawing(width, height)

        assertThat(drawing).isEqualTo(
                Drawing2().withWidth(width).withHeight(height).withShapes(listOf(Shape2(
                        setOf(Vertex2(.0, .0), Vertex2(width, .0), Vertex2(width, height), Vertex2(.0, height)), color = SOLID_BLACK
                )))
        )
    }
}
