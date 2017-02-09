package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.*
import io.github.mamachanko.instructions.*
import org.assertj.core.error.ShouldHave
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy

class InstructionFeatureTests {

    @Test
    fun `should create drawing from list of instructions`() {
        val instructions = StartBy()
                .adding().a().rectangle().fillingThePage()
                .then()
                .add().two().rectangles().fillingThePage()
                .then()
                .discard().one()
                .then()
                .duplicate().all()
                .then()
                .colorise().half().from(GrayPalette())

        val drawing = GivenABlank()
                .withWidth(600.0).and().withHeight(800.0)
                .follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)
        drawing.shapes.map {
            assertThat(it.vertices).containsExactly(
                    Vertex2(.0, .0), Vertex2(600.0, .0), Vertex2(600.0, 800.0), Vertex2(.0, 800.0)
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
                .adding().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(20.0)
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all().from(ColorPalette(color1, color2))

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(12)
        assertThat(drawing.shapes.map { it.color }.toSet()).containsExactly(color1, color2)

        // assert that drawing contains all initial rectangles' corner vertices
        val rectangleCornerVertices = listOf(
                Vertex2(20.0, 20.0), Vertex2(220.0, 20.0), Vertex2(220.0, 120.0), Vertex2(20.0, 120.0),
                Vertex2(240.0, 20.0), Vertex2(440.0, 20.0), Vertex2(440.0, 120.0), Vertex2(240.0, 120.0),
                Vertex2(240.0, 140.0), Vertex2(220.0, 140.0), Vertex2(220.0, 240.0), Vertex2(20.0, 240.0),
                Vertex2(240.0, 140.0), Vertex2(440.0, 140.0), Vertex2(440.0, 240.0), Vertex2(240.0, 240.0),
                Vertex2(20.0, 260.0), Vertex2(220.0, 260.0), Vertex2(220.0, 360.0), Vertex2(20.0, 360.0),
                Vertex2(240.0, 260.0), Vertex2(440.0, 260.0), Vertex2(440.0, 360.0), Vertex2(240.0, 260.0)
        )
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllIn(rectangleCornerVertices)

        // assert that the intersection of initial rectangles' corner vertices and the rest is of size 12
        assertThat(
                drawing.shapes.map { it.vertices }.flatMap { it }.toSet().intersect(rectangleCornerVertices)
        ).hasSize(12)
    }
}