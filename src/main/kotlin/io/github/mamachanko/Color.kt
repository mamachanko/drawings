package io.github.mamachanko

import java.util.*

val SOLID = 1.0
val SOLID_BLACK = Color(0, 0, 0, 1.0)

data class Color(val red: Int, val green: Int, val blue: Int, val alpha: Double)

interface Palette {
    val color: Color
}

class RandomPalette : Palette {
    override val color: Color
        get() {
            val random = Random()
            return Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextDouble())
        }
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