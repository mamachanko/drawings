package io.github.mamachanko

import io.github.mamachanko.color.Palette
import io.github.mamachanko.instructions.Drawing
import io.github.mamachanko.instructions.GivenABlankDrawing
import io.github.mamachanko.instructions.Instruction
import org.springframework.stereotype.Service
import java.util.*

@Service
class DrawingService(val instructionsLibrary: Set<Instruction>, val palettesLibrary: Set<Palette>) {

    fun getDrawing(width: Double, height: Double): Drawing {
        return GivenABlankDrawing()
                .withWidth(width)
                .withHeight(height)
                .withPalette(palette)
                .follow(instruction)
    }

    private val palette: Palette
        get() = oneOf(palettesLibrary) as Palette

    private val instruction: Instruction
        get() = oneOf(instructionsLibrary) as Instruction

}
