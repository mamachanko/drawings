package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class SliceTests {

    @Test
    fun `should return two shapes when slicing one`() {

        val instructions = Add().one().rectangle().then().slice().randomly()

        val drawing = GivenABlankDrawing().withWidth(100.0).and().withHeight(100.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(2)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(8)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(6)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllOf(Vertex(.0, .0), Vertex(100.0, .0), Vertex(100.0, 100.0), Vertex(.0, 100.0))
        val vertices1 = drawing.shapes[0].vertices
        val vertices2 = drawing.shapes[1].vertices
        assertThat(vertices1.intersect(vertices2)).hasSize(2)
    }

}