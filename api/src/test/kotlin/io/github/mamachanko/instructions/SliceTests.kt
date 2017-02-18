package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import io.github.mamachanko.geometry.convexHull
import org.junit.Test

class SliceTests {

    @Test
    fun `should return two shapes with half proportions when slicing rectangle through middle of edges`() {
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
