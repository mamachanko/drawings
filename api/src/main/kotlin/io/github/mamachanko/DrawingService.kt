package io.github.mamachanko

import io.github.mamachanko.instructions.Drawing2
import io.github.mamachanko.instructions.GivenABlank
import io.github.mamachanko.instructions.Instruction
import org.springframework.stereotype.Service
import java.util.*

@Service
class DrawingService(val instructions: Set<List<Instruction>>) {

    fun getDrawing(width: Double, height: Double): Drawing2 {
        return GivenABlank().withWidth(width).withHeight(height).follow(oneOf(instructions))
    }

    private fun oneOf(collection: Set<List<Instruction>>): List<Instruction> {
        return collection.toList()[Random().nextInt(collection.size)]
    }
}
