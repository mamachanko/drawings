package io.github.mamachanko.instructions

import com.google.common.truth.Truth
import org.junit.Test

class AddTests {

    @Test
    fun `should be able to slice shapes`() {

        val instructions = Add().one().rectangle().then().slice().randomly()

        val drawing = GivenABlank().withWidth(100.0).and().withHeight(100.0).follow(instructions.asList())

        Truth.assertThat(drawing.shapes).hasSize(2)
        Truth.assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(8)
        Truth.assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(6)
        Truth.assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllOf(Vertex2(.0, .0), Vertex2(100.0, .0), Vertex2(100.0, 100.0), Vertex2(.0, 100.0))
        val vertices1 = drawing.shapes[0].vertices
        val vertices2 = drawing.shapes[1].vertices
        Truth.assertThat(vertices1.intersect(vertices2)).hasSize(2)
    }

}