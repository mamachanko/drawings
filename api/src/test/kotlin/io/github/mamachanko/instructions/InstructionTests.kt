package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.*
import io.github.mamachanko.color.*
import io.github.mamachanko.geometry.Vertex
import io.github.mamachanko.instructions.*
import org.assertj.core.error.ShouldHave
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy

class InstructionTests {

    @Test
    fun `should create drawing containing a sliced rectangle`() {
        val drawing = GivenABlankDrawing()
                .withWidth(100.0).and()
                .withHeight(100.0)
                .withPalette(GrayPalette())
                .follow(StartBy()
                        .adding().one().rectangle()
                        .then()
                        .slice().vertically()
                        .then()
                        .colorise().all())

        assertThat(drawing.shapes).hasSize(2)
    }

    @Test
    fun `should create drawing from instruction`() {
        val instruction = StartBy()
                .adding().a().rectangle()
                .then()
                .add().two().rectangles()
                .then()
                .discard().one()
                .then()
                .duplicate().all()
                .then()
                .colorise().half()

        val drawing = GivenABlankDrawing()
                .withWidth(600.0).and()
                .withHeight(800.0).and()
                .withPalette(GrayPalette())
                .follow(instruction)

        assertThat(drawing.shapes).hasSize(4)
        drawing.shapes.map {
            assertThat(it.vertices).containsExactly(
                    Vertex(.0, .0), Vertex(600.0, .0), Vertex(600.0, 800.0), Vertex(.0, 800.0)
            )
        }
        assertThat(drawing.shapes.filter { it.color.equals(BLACK) }).hasSize(2)
        assertThat(drawing.shapes.filter { !it.color.equals(BLACK) }).hasSize(2)
    }

    @Test
    fun `should create the baseline drawing from instructions`() {
        val color1 = Color(10, 20, 30)
        val color2 = Color(40, 50, 60)
        val instruction = StartBy()
                .adding().rectangles().to(aGridOf(2 x 3).withCollapsedMargin(20.0))
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all()

        val drawing = GivenABlankDrawing()
                .withWidth(460.0).and()
                .withHeight(380.0)
                .withPalette(ColorPalette(color1, color2))
                .follow(instruction)

        assertThat(drawing.shapes).hasSize(12)
        assertThat(drawing.shapes.map { it.color }.toSet()).containsExactly(color1, color2)
    }
}