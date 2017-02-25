package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import io.github.mamachanko.geometry.convexHull
import org.junit.Test

class SliceTests {

    @Test
    fun `should return two identical and smaller rectangles when slicing rectangle vertically in halves`() {
        val rectangle = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(rectangle).follow(
                Slice().vertically().withHalfProportions()
        )

        assertThat(drawing.shapes).containsExactly(
                Shape(setOf(Vertex(.0, .0), Vertex(.5, .0), Vertex(.5, 1.0), Vertex(.0, 1.0))),
                Shape(setOf(Vertex(.5, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.5, 1.0)))
        )
    }

    @Test
    fun `should return two shapes by slicing through middle of edges when slicing rectangle with half proportions`() {
        val rectangle = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(rectangle).follow(
                Slice().withHalfProportions()
        )

        drawing.assertThatItIsSliceResultOf(rectangle)
        drawing.vertices.toSet().minus(drawing.vertices.convexHull).map {
            assertThat(it).isAnyOf(Vertex(.5, .0), Vertex(1.0, .5), Vertex(.5, 1.0), Vertex(.0, .5))
        }
    }

    @Test
    fun `should return two shapes by slicings edges with golden proportions when slicing rectangle with golden proportions`() {
        val rectangle = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(rectangle).follow(
                Slice().withGoldenProportions()
        )

        drawing.assertThatItIsSliceResultOf(rectangle)
        drawing.vertices.toSet().minus(drawing.vertices.convexHull).map {
            assertThat(it).isAnyOf(
                    Vertex(.0, .382),
                    Vertex(.0, .618),
                    Vertex(.382, .0),
                    Vertex(.618, .0),
                    Vertex(1.0, .382),
                    Vertex(1.0, .618),
                    Vertex(.382, 1.0),
                    Vertex(.618, 1.0)
            )
        }
    }

    @Test
    fun `should return two shapes when slicing rectangle randomly`() {
        val rectangle = Shape(setOf(Vertex(.0, .0), Vertex(1.0, .0), Vertex(1.0, 1.0), Vertex(.0, 1.0)))
        val drawing = Drawing().withShapes(rectangle).follow(
                Slice()
        )

        drawing.assertThatItIsSliceResultOf(rectangle)
    }

    private fun Drawing.assertThatItIsSliceResultOf(original: Shape): Unit {
        assertThat(shapes).hasSize(2)
        assertThat(shapes.toSet()).hasSize(2)
        assertThat(vertices.convexHull).isEqualTo(original.vertices)
        assertThat(vertices.toSet().minus(original.vertices.convexHull)).hasSize(2)
    }

}
