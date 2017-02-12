package io.github.mamachanko.color

val SOLID = 100
val SOLID_BLACK = Color(0, 0, 0, 100)

data class Color(val red: Int, val green: Int, val blue: Int, val alpha: Int) {

    companion object {

        fun fromHex(hex: String): Color {
            return Color(red = hex.substring(0..1).asInt(),
                    green = hex.substring(2..3).asInt(),
                    blue = hex.substring(4..5).asInt(),
                    alpha = SOLID)
        }

        private fun  String.asInt(): Int = Integer.parseInt(this, 16)
    }
    }
