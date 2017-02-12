package io.github.mamachanko.unit

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.util.UriComponentsBuilder

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DrawingFeatureTests {

    @LocalServerPort
    private var serverPort: Int = 0

    @Autowired
    lateinit private var httpClient: TestRestTemplate

    @Test
    fun `As an API client I can create Drawings`() {
        val requestUri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(serverPort)
                .path("/api/drawings")
                .queryParam("width", 625)
                .queryParam("height", 475)
                .toUriString()
        val drawingResponse = httpClient.postForEntity(requestUri, emptyMap<String, String>(), DrawingDetails::class.java)

        assertThat(drawingResponse.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(drawingResponse.headers["Content-Type"]).isEqualTo(listOf(APPLICATION_JSON_UTF8_VALUE))
        assertThat(drawingResponse.body.shapes.size).isGreaterThan(0)
        drawingResponse.body.shapes.map {
            assertThat(it.vertices.size).isGreaterThan(0)
        }
    }
}

class DrawingDetails {
    lateinit var shapes: List<ShapeDetails>
}

class ShapeDetails {
    lateinit var vertices: List<VertexDetails>
    lateinit var color: ColorDetails
}

class ColorDetails {
    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0
    var alpha: Int = 0
}

class VertexDetails {
    var x: Double = .0
    var y: Double = .0
}
