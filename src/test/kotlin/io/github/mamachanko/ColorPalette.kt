package io.github.mamachanko

import io.github.mamachanko.Shape.Companion.SOLID_BLACK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ColorPaletteTests {

    @Test
    fun `should return an RGB color`() {
        assertThat(ColorPalette().color).isInstanceOf(Color::class.java)
    }
}

class ColorPalette {
    val color: Color
        get() {
            return SOLID_BLACK
        }
}
