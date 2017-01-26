package io.github.mamachanko

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.both
import org.hamcrest.Matchers.*
import org.hamcrest.core.Every.everyItem
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(MockitoJUnitRunner::class)
class ApiTests {

    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(Api(ShapesService())).build()
    }

    @Test
    fun `should return single shape of WIDTH x HEIGHT for GET`() {
        val width = 123.4
        val height = 567.89

        val aBrightnessValue = both(greaterThanOrEqualTo(0)).and(lessThan(256))
        val anAlphaValue = both(greaterThanOrEqualTo(.0)).and(lessThanOrEqualTo(1.0))
        val withinWidth = both(greaterThanOrEqualTo(.0)).and(lessThanOrEqualTo(width))
        val withinHeight = both(greaterThanOrEqualTo(.0)).and(lessThanOrEqualTo(height))

        mockMvc.perform(get("/api/shapes?width=$width&height=$height"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.shapes.length()").value(`is`(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.shapes.[*].color.red").value(everyItem(`is`(aBrightnessValue))))
                .andExpect(jsonPath("$.shapes.[*].color.blue").value(everyItem(`is`(aBrightnessValue))))
                .andExpect(jsonPath("$.shapes.[*].color.green").value(everyItem(`is`(aBrightnessValue))))
                .andExpect(jsonPath("$.shapes.[*].color.alpha").value(everyItem(`is`(anAlphaValue))))
                .andExpect(jsonPath("$.shapes[*].vertices[*].x").value(everyItem(`is`(withinWidth))))
                .andExpect(jsonPath("$.shapes[*].vertices[*].y").value(everyItem(`is`(withinHeight))))
    }
}
