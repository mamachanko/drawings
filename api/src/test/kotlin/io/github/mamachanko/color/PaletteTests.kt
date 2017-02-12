package io.github.mamachanko.unit

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.color.*
import org.junit.Test

class BlackPaletteTests {

    @Test
    fun `should return black`() {
        assertThat(BlackPalette().color).isEqualTo(Color(0, 0, 0))
    }

}

class GrayPaletteTests {

    @Test
    fun `should return a gray`() {
        val gray = GrayPalette().color
        assertThat(gray.red).isEqualTo(gray.green)
        assertThat(gray.red).isEqualTo(gray.blue)
    }

    @Test
    fun `should return a random gray`() {
        assertThat(GrayPalette().color).isNotEqualTo(GrayPalette().color)
    }

}

class ColorPaletteTests {

    @Test
    fun `should be equal if all colors are equal`() {
        val color1 = Color(1, 2, 3)
        val color2 = Color(4, 6, 7)

        assertThat(ColorPalette(color1, color2)).isEqualTo(ColorPalette(color2, color1))
    }

    @Test
    fun `should not be equal if values differ`() {
        val color1 = Color(1, 2, 3)
        val color2 = Color(4, 6, 7)

        assertThat(ColorPalette(color1)).isNotEqualTo(ColorPalette(color2))
    }

    @Test
    fun `should always return the same color if given one`() {
        val green = Color(120, 240, 120)
        assertThat(ColorPalette(green).color).isEqualTo(ColorPalette(green).color)
    }

    @Test
    fun `should return any of the given colors`() {
        val green = Color(120, 240, 120)
        val red = Color(180, 45, 45)
        val blue = Color(0, 0, 190)

        assertThat(ColorPalette(red, blue).color).isNotEqualTo(ColorPalette(green).color)
    }
}

class RandomPaletteTests {
    @Test
    fun `should return random color`() {
        assertThat(RandomPalette().color).isNotEqualTo(RandomPalette().color)
    }
}

