package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Vertex
import org.junit.Test

class AddTest {

    @Test
    fun `should add single rectangle as big as the drawing`() {
        val drawing = GivenABlankDrawing()
                .withWidth(460.0).and()
                .withHeight(380.0)
                .follow(
                        Add().a().rectangle()
                )

        assertThat(drawing.shapes).hasSize(1)
        assertThat(drawing.vertices.toSet()).containsAllOf(
                Vertex(.0, .0), Vertex(460.0, .0), Vertex(460.0, 380.0), Vertex(.0, 380.0)
        )
        assertThat(drawing.vertices.toSet()).hasSize(4)
    }

    @Test
    fun `should add three rectangles as big as the drawing`() {
        val drawing = GivenABlankDrawing()
                .withWidth(460.0).and()
                .withHeight(380.0)
                .follow(
                        Add().three().rectangles()
                )

        assertThat(drawing.shapes).hasSize(3)
        assertThat(drawing.vertices.toSet()).containsAllOf(
                Vertex(.0, .0), Vertex(460.0, .0), Vertex(460.0, 380.0), Vertex(.0, 380.0)
        )
        assertThat(drawing.vertices).hasSize(12)
    }

    @Test
    fun `should add rectangles to a 2 x 2 grid`() {
        val drawing = GivenABlankDrawing()
                .withWidth(2.0).and()
                .withHeight(2.0)
                .follow(
                        Add().rectangles().to(aGridOf(2 x 2))
                )

        assertThat(drawing.shapes).hasSize(4)
        assertThat(drawing.vertices).containsExactly(
                Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0),
                Vertex(1.0, .0), Vertex(2.0, .0), Vertex(2.0, 1.0), Vertex(1.0, 1.0),
                Vertex(.0, 1.0), Vertex(1.0, 1.0), Vertex(1.0, 2.0), Vertex(.0, 2.0),
                Vertex(1.0, 1.0), Vertex(2.0, 1.0), Vertex(2.0, 2.0), Vertex(1.0, 2.0)
        )
        assertThat(drawing.vertices).hasSize(4 * 4)
        assertThat(drawing.vertices.toSet()).hasSize((2 + 1) * (2 + 1))
    }

    @Test
    fun `should add rectangles to a 400 x 600 grid`() {
        val drawing = GivenABlankDrawing()
                .withWidth(400.0).and()
                .withHeight(600.0)
                .follow(
                        Add().rectangles().to(aGridOf(400 x 600))
                )

        assertThat(drawing.shapes).hasSize(400 * 600)
        assertThat(drawing.vertices).hasSize(4 * 400 * 600)
        assertThat(drawing.vertices.toSet()).hasSize((400 + 1) * (600 + 1))
    }

    @Test
    fun `should add rectangles to a 2 x 3 grid with collapsed margins`() {
        val drawing = GivenABlankDrawing()
                .withWidth(460.0).and()
                .withHeight(380.0)
                .follow(
                        Add().rectangles().to(aGridOf(2 x 3).withCollapsedMargin(20.0))
                )

        assertThat(drawing.shapes).hasSize(6)
        assertThat(drawing.vertices).containsExactly(
                Vertex(20.0, 20.0), Vertex(220.0, 20.0), Vertex(220.0, 120.0), Vertex(20.0, 120.0),
                Vertex(240.0, 20.0), Vertex(440.0, 20.0), Vertex(440.0, 120.0), Vertex(240.0, 120.0),
                Vertex(20.0, 140.0), Vertex(220.0, 140.0), Vertex(220.0, 240.0), Vertex(20.0, 240.0),
                Vertex(240.0, 140.0), Vertex(440.0, 140.0), Vertex(440.0, 240.0), Vertex(240.0, 240.0),
                Vertex(20.0, 260.0), Vertex(220.0, 260.0), Vertex(220.0, 360.0), Vertex(20.0, 360.0),
                Vertex(240.0, 260.0), Vertex(440.0, 260.0), Vertex(440.0, 360.0), Vertex(240.0, 360.0)
        )
        assertThat(drawing.vertices).hasSize(4 * 6)
        assertThat(drawing.vertices.toSet()).hasSize(((2 + 1) * (3 + 1)) * 2)
    }

    @Test
    fun `should add three rectangles to a 2 x 3 grid`() {
        val drawing = GivenABlankDrawing()
                .withWidth(20.0).and()
                .withHeight(30.0)
                .follow(
                        Add().three().rectangles().to(aGridOf(2 x 3))
                )

        assertThat(drawing.shapes).hasSize(3)
        assertThat(drawing.vertices).containsExactly(
                Vertex(.0, .0), Vertex(10.0, .0), Vertex(10.0, 10.0), Vertex(.0, 10.0),
                Vertex(10.0, .0), Vertex(20.0, .0), Vertex(20.0, 10.0), Vertex(10.0, 10.0),
                Vertex(.0, 10.0), Vertex(10.0, 10.0), Vertex(10.0, 20.0), Vertex(.0, 20.0)
        )
    }

    @Test
    fun `should add nine rectangles to a 2 x 2 grid`() {
        val drawing = GivenABlankDrawing()
                .withWidth(2.0).and()
                .withHeight(2.0)
                .follow(
                        Add(9).rectangles().to(aGridOf(2 x 2))
                )

        assertThat(drawing.shapes).hasSize(9)
    }

    @Test
    fun `should add one rectangle randomly to a 2 x 2 grid`() {
        val drawing = GivenABlankDrawing()
                .withWidth(2.0).and()
                .withHeight(2.0)
                .follow(
                        Add().one().rectangle().randomly().to(aGridOf(2 x 2))
                )

        assertThat(drawing.shapes).hasSize(1)
        assertThat(drawing.shapes.first().path.first()).isAnyOf(
                Vertex(.0, .0), Vertex(1.0, .0), Vertex(.0, 1.0), Vertex(1.0, 1.0)
        )
    }

    @Test
    fun `should one rectangle randomly`() {
        val drawing = GivenABlankDrawing()
                .withWidth(2.0).and()
                .withHeight(2.0)
                .follow(
                        Add().one().rectangle().randomly()
                )

        assertThat(drawing.shapes).hasSize(1)
    }
}