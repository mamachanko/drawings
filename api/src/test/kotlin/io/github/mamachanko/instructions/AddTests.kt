package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class AddTests {

    @Test
    fun `should add single rectangle as big as the drawing`() {
        val instructions = Add().a().rectangle().fillingThePage()

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(1)
        val rectangleCornerVertices = listOf(
                Vertex(.0, .0), Vertex(460.0, .0), Vertex(460.0, 380.0), Vertex(.0, 380.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllIn(rectangleCornerVertices)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(4)
    }

    @Test
    fun `should add two rectangles as big as the drawing`() {
        val instructions = Add().two().rectangles().fillingThePage()

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(2)

        val rectangleCornerVertices = listOf(
                Vertex(.0, .0), Vertex(460.0, .0), Vertex(460.0, 380.0), Vertex(.0, 380.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllIn(rectangleCornerVertices)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(8)
    }

    @Test
    fun `should add rectangles layed out in 2x2 grid`() {
        val instructions = Add().rectangles().inAGridOf(2, 2)

        val drawing = GivenABlank().withWidth(2.0).and().withHeight(2.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)

        val rectangleCornerVertices = listOf(
                Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0),
                Vertex(1.0, .0), Vertex(2.0, .0), Vertex(2.0, 1.0), Vertex(1.0, 1.0),
                Vertex(.0, 1.0), Vertex(1.0, 1.0), Vertex(1.0, 2.0), Vertex(.0, 2.0),
                Vertex(1.0, 1.0), Vertex(2.0, 1.0), Vertex(2.0, 2.0), Vertex(1.0, 2.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).containsExactlyElementsIn(rectangleCornerVertices)

        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(4 * 4)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize((2 + 1) * (2 + 1))
    }

    @Test
    fun `should add rectangles layed out in 400 x 600 grid`() {
        val instructions = Add().rectangles().inAGridOf(400, 600)

        val drawing = GivenABlank().withWidth(400.0).and().withHeight(600.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(400 * 600)

        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(4 * 400 * 600)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize((400 + 1) * (600 + 1))
    }

    @Test
    fun `should add rectangles layed out in grid with collapsed margins`() {
        val instructions = Add().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(20.0)

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(6)

        val rectangleCornerVertices = listOf(
                Vertex(20.0, 20.0), Vertex(220.0, 20.0), Vertex(220.0, 120.0), Vertex(20.0, 120.0),
                Vertex(240.0, 20.0), Vertex(440.0, 20.0), Vertex(440.0, 120.0), Vertex(240.0, 120.0),
                Vertex(20.0, 140.0), Vertex(220.0, 140.0), Vertex(220.0, 240.0), Vertex(20.0, 240.0),
                Vertex(240.0, 140.0), Vertex(440.0, 140.0), Vertex(440.0, 240.0), Vertex(240.0, 240.0),
                Vertex(20.0, 260.0), Vertex(220.0, 260.0), Vertex(220.0, 360.0), Vertex(20.0, 360.0),
                Vertex(240.0, 260.0), Vertex(440.0, 260.0), Vertex(440.0, 360.0), Vertex(240.0, 360.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).containsExactlyElementsIn(rectangleCornerVertices)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(4 * 6)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(((2 + 1) * (3 + 1)) * 2)

    }
}