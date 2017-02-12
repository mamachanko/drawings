package io.github.mamachanko

import io.github.mamachanko.color.Palette
import io.github.mamachanko.instructions.Drawing
import io.github.mamachanko.instructions.GivenABlankDrawing
import io.github.mamachanko.instructions.Instruction
import org.springframework.stereotype.Service
import java.util.*

@Service
class DrawingService(val instructionsLibrary: Set<List<Instruction>>, val palettesLibrary: Set<Palette>) {

    fun getDrawing(width: Double, height: Double): Drawing {
        return GivenABlankDrawing()
                .withWidth(width)
                .withHeight(height)
                .withPalette(palette)
                .follow(instructions)
    }

    private val palette: Palette
        get() = oneOf(palettesLibrary) as Palette

    private val instructions: List<Instruction>
        get() = oneOf(instructionsLibrary) as List<Instruction>

    private fun oneOf(collection: Set<Any>): Any {
        return collection.toList()[Random().nextInt(collection.size)]
    }
}
