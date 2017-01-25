package io.github.mamachanko

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(MockitoJUnitRunner::class)
class WebTests {

    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(Web()).build()
    }

    @Test
    fun `should return shapes within boundaries for GET request`() {
        mockMvc.perform(get("/api/shapes")).andExpect(status().isOk())
    }
}
