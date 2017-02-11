package io.github.mamachanko.color

import java.util.*

interface Palette {
    val color: Color
}

class RandomPalette : Palette {
    override val color: Color
        get() = Color(
                red = randomBrightness(),
                green = randomBrightness(),
                blue = randomBrightness(),
                alpha = randomAlpha()
        )

    private fun randomBrightness() = Random().nextInt(256)

    private fun randomAlpha() = Random().nextInt(SOLID + 1)
}

class ColorPalette(vararg val colors: Color) : Palette {
    override val color: Color
        get() {
            return colors[Random().nextInt(colors.size)]
        }
}

class GrayPalette : Palette {
    override val color: Color
        get() {
            val brightness = Random().nextInt(256)
            return Color(brightness, brightness, brightness, SOLID)
        }
}

class BlackPalette : Palette {
    override val color: Color
        get() {
            return SOLID_BLACK
        }
}