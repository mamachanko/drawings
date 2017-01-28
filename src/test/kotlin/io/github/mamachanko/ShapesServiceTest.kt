package io.github.mamachanko

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.both
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Test

class ShapesServiceTest {

    @Test
    fun `should return shapes within given box`() {
        val width = 123.45
        val height = 456.78

        val shapes = ShapesService().getShapesWithin(width, height)

        val withinWidth = both(Matchers.greaterThanOrEqualTo(.0)).and(Matchers.lessThanOrEqualTo(width))
        val withinHeight = both(Matchers.greaterThanOrEqualTo(.0)).and(Matchers.lessThanOrEqualTo(height))

        assertThat(shapes, hasSize(greaterThanOrEqualTo(1)))
        shapes.forEach { shape ->
            shape.vertices.forEach { vertex ->
                assertThat(vertex.x, `is`(withinWidth))
                assertThat(vertex.y, `is`(withinHeight))
            }
        }
    }

}