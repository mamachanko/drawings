package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AddTests {

    @Test
    fun `should add single rectangle as big as the drawing`() {
        val instructions = Add().a().rectangle().fillingThePage()

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(1)

        // assert that drawing contains all rectangles' corner vertices
        val rectangleCornerVertices = listOf(
                Vertex2(.0, .0), Vertex2(460.0, .0), Vertex2(460.0, 380.0), Vertex2(.0, 380.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllIn(rectangleCornerVertices)
        // TODO: this is redundant
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(4)
    }

    @Test
    fun `should add two rectangles as big as the drawing`() {
        val instructions = Add().two().rectangles().fillingThePage()

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(2)

        // assert that drawing contains all rectangles' corner vertices
        val rectangleCornerVertices = listOf(
                Vertex2(.0, .0), Vertex2(460.0, .0), Vertex2(460.0, 380.0), Vertex2(.0, 380.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllIn(rectangleCornerVertices)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(8)
    }

    @Test
    fun `should add rectangles layed out in grid`() {
        val instructions = Add().rectangles().inAGridOf(2, 2)

        val drawing = GivenABlank().withWidth(2.0).and().withHeight(2.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)

        // assert that drawing contains all rectangles' corner vertices
        val rectangleCornerVertices = listOf(
                Vertex2(.0, .0), Vertex2(1.0, .0), Vertex2(1.0, 1.0), Vertex2(.0, 1.0),
                Vertex2(1.0, .0), Vertex2(2.0, .0), Vertex2(2.0, 1.0), Vertex2(1.0, 1.0),
                Vertex2(.0, 1.0), Vertex2(1.0, 1.0), Vertex2(1.0, 2.0), Vertex2(.0, 2.0),
                Vertex2(1.0, 1.0), Vertex2(2.0, 1.0), Vertex2(2.0, 2.0), Vertex2(1.0, 2.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).containsExactlyElementsIn(rectangleCornerVertices)
        // TODO: this is redundant
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(16)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(9)
    }

    @Test
    fun `should add rectangles layed out in grid with collapsed margins`() {
        val instructions = Add().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(20.0)

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(6)

        // assert that drawing contains all rectangles' corner vertices
        val rectangleCornerVertices = listOf(
                Vertex2(20.0, 20.0), Vertex2(220.0, 20.0), Vertex2(220.0, 120.0), Vertex2(20.0, 120.0),
                Vertex2(240.0, 20.0), Vertex2(440.0, 20.0), Vertex2(440.0, 120.0), Vertex2(240.0, 120.0),
                Vertex2(240.0, 140.0), Vertex2(220.0, 140.0), Vertex2(220.0, 240.0), Vertex2(20.0, 240.0),
                Vertex2(240.0, 140.0), Vertex2(440.0, 140.0), Vertex2(440.0, 240.0), Vertex2(240.0, 240.0),
                Vertex2(20.0, 260.0), Vertex2(220.0, 260.0), Vertex2(220.0, 360.0), Vertex2(20.0, 360.0),
                Vertex2(240.0, 260.0), Vertex2(440.0, 260.0), Vertex2(440.0, 360.0), Vertex2(240.0, 260.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).containsExactlyElementsIn(rectangleCornerVertices)
        // TODO: this is redundant
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(24)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(24)

    }
}