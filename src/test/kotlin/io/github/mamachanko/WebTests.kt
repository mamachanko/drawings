package io.github.mamachanko

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(MockitoJUnitRunner::class)
class ApiTests {

    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(Api()).build()
    }

    @Test
    fun `should return single shape of WIDTH x HEIGHT for GET`() {
        mockMvc.perform(get("/api/shapes?width=123.4&height=567.89"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.shapes.length()").value(1))

                .andExpect(jsonPath("$.shapes.[0].color.red").value(0))
                .andExpect(jsonPath("$.shapes.[0].color.green").value(0))
                .andExpect(jsonPath("$.shapes.[0].color.blue").value(0))
                .andExpect(jsonPath("$.shapes.[0].color.alpha").value(1.0))

                .andExpect(jsonPath("$.shapes[0].vertices[0].x").value(.0))
                .andExpect(jsonPath("$.shapes[0].vertices[0].y").value(.0))

                .andExpect(jsonPath("$.shapes[0].vertices[1].x").value(123.4))
                .andExpect(jsonPath("$.shapes[0].vertices[1].y").value(.0))

                .andExpect(jsonPath("$.shapes[0].vertices[2].x").value(123.4))
                .andExpect(jsonPath("$.shapes[0].vertices[2].y").value(567.89))

                .andExpect(jsonPath("$.shapes[0].vertices[3].x").value(.0))
                .andExpect(jsonPath("$.shapes[0].vertices[3].y").value(567.89))
    }
}

@RunWith(MockitoJUnitRunner::class)
class WebTests {

    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(Web()).build()
    }

    @Test
    fun `should return index`() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.view().name("index"))
    }
}
