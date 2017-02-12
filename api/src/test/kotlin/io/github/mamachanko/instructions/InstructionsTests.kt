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

class InstructionsTests {

    @Test
    fun `should create drawing from list of instructions`() {
        val instructions = StartBy()
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
                .follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)
        drawing.shapes.map {
            assertThat(it.vertices).containsExactly(
                    Vertex(.0, .0), Vertex(600.0, .0), Vertex(600.0, 800.0), Vertex(.0, 800.0)
            )
        }
        assertThat(drawing.shapes.filter { it.color.equals(SOLID_BLACK) }).hasSize(2)
        assertThat(drawing.shapes.filter { !it.color.equals(SOLID_BLACK) }).hasSize(2)
    }

    @Test
    fun `should create the baseline Kelleybert drawing from list of instructions`() {
        val color1 = Color(10, 20, 30, SOLID)
        val color2 = Color(40, 50, 60, SOLID)
        val instructions = StartBy()
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
                .follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(12)
        assertThat(drawing.shapes.map { it.color }.toSet()).containsExactly(color1, color2)
    }
}