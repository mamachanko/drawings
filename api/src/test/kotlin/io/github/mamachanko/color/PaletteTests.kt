package io.github.mamachanko.unit

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.color.*
import org.junit.Test

class BlackPaletteTests {

    @Test
    fun `should return solid black`() {
        assertThat(BlackPalette().color).isEqualTo(Color(0, 0, 0, 100))
    }

}

class GrayPaletteTests {

    @Test
    fun `should return solid gray`() {
        val gray = GrayPalette().color
        assertThat(gray.red).isEqualTo(gray.green)
        assertThat(gray.red).isEqualTo(gray.blue)
        assertThat(gray.alpha).isEqualTo(100)
    }

    @Test
    fun `should return random solid gray`() {
        assertThat(GrayPalette().color).isNotEqualTo(GrayPalette().color)
    }

}

class ColorPaletteTests {

    @Test
    fun `should be equal if all colors are equal`() {
        val color1 = Color(1, 2, 3, 4)
        val color2 = Color(4, 6, 7, 8)

        assertThat(ColorPalette(color1, color2)).isEqualTo(ColorPalette(color2, color1))
    }

    @Test
    fun `should not be equal if values differ`() {
        val color1 = Color(1, 2, 3, 4)
        val color2 = Color(4, 6, 7, 8)

        assertThat(ColorPalette(color1)).isNotEqualTo(ColorPalette(color2))
    }

    @Test
    fun `should always return the same color if given one`() {
        val green = Color(120, 240, 120, 65)
        assertThat(ColorPalette(green).color).isEqualTo(ColorPalette(green).color)
    }

    @Test
    fun `should return any of the given colors`() {
        val green = Color(120, 240, 120, 50)
        val red = Color(180, 45, 45, 100)
        val blue = Color(0, 0, 190, 100)

        assertThat(ColorPalette(red, blue).color).isNotEqualTo(ColorPalette(green).color)
    }
}

class RandomPaletteTests {
    @Test
    fun `should return random color`() {
        assertThat(RandomPalette().color).isNotEqualTo(RandomPalette().color)
    }
}

