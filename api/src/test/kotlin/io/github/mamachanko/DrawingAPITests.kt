package io.github.mamachanko.unit

import io.github.mamachanko.Color
import io.github.mamachanko.DrawingAPI
import io.github.mamachanko.DrawingService
import io.github.mamachanko.instructions.Drawing2
import io.github.mamachanko.instructions.Shape2
import io.github.mamachanko.instructions.Vertex2
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest(DrawingAPI::class)
class DrawingAPITests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var drawingService: DrawingService

    @Test
    fun `should return drawing when creating`() {
        val width = 123.4
        val height = 567.89

        given(drawingService.getDrawing(width, height)).willReturn(simpleDrawing(height, width))

        mockMvc.perform(post("/api/drawings?width=$width&height=$height"))
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.shapes.length()").value(1))
                .andExpect(jsonPath("$.shapes.[0].color.red").value(12))
                .andExpect(jsonPath("$.shapes.[0].color.green").value(34))
                .andExpect(jsonPath("$.shapes.[0].color.blue").value(56))
                .andExpect(jsonPath("$.shapes.[0].color.alpha").value(78))
                .andExpect(jsonPath("$.shapes.[0].vertices.[0].x").value(.0))
                .andExpect(jsonPath("$.shapes.[0].vertices.[0].y").value(.0))
                .andExpect(jsonPath("$.shapes.[0].vertices.[1].x").value(width))
                .andExpect(jsonPath("$.shapes.[0].vertices.[1].y").value(.0))
                .andExpect(jsonPath("$.shapes.[0].vertices.[2].x").value(width))
                .andExpect(jsonPath("$.shapes.[0].vertices.[2].y").value(height))
                .andExpect(jsonPath("$.shapes.[0].vertices.[3].x").value(.0))
                .andExpect(jsonPath("$.shapes.[0].vertices.[3].y").value(height))
    }

    private fun simpleDrawing(height: Double, width: Double): Drawing2 {
        return Drawing2()
                .withShapes(listOf(Shape2(setOf(Vertex2(.0, .0), Vertex2(width, .0), Vertex2(width, height), Vertex2(.0, height)), color = Color(12, 34, 56, 78))))
    }
}
