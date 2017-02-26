package io.github.mamachanko.instructions

import io.github.mamachanko.geometry.Shape
import io.github.mamachanko.geometry.Vertex
import io.github.mamachanko.geometry.average
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class Rotate(priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    private var rotateBy: Angle = Angle.Random

    override fun applyTo(drawing: Drawing): Drawing {
        return drawing.withShapes(drawing.shapes.map { rotate(it) })
    }

    private val rotate: (Shape) -> Shape = { shape ->
        when (rotateBy) {
            Angle.FortyFive -> 45.0
            Angle.Ninety -> 90.0
            Angle.Random -> Random().nextDouble() * 360
        }.let { angle ->
            shape.vertices.average.let { centre ->
                Shape().withVertices(*shape.vertices.map {
                    Vertex(it.x - centre.x, it.y - centre.y)
                }.map { rotate(it, angle)
                }.map {
                    Vertex(it.x + centre.x, it.y + centre.y)
                }.toTypedArray())
            }
        }
    }

    private fun rotate(v: Vertex, degrees: Double): Vertex {
        val cos = Math.cos(Math.toRadians(degrees))
        val sin = Math.sin(Math.toRadians(degrees))
        return Vertex(
                round(v.x * cos - v.y * sin),
                round(v.x * sin + v.y * cos)
        )
    }

    private fun round(value: Double): Double {
        return BigDecimal(value).setScale(10, RoundingMode.HALF_UP).toDouble()
    }

    fun by(): Rotate = this

    fun degrees(): Rotate = this

    fun ninety(): Rotate = this.apply { rotateBy = Angle.Ninety }

    fun fortyFive(): Rotate = this.apply { rotateBy = Angle.FortyFive }

    fun randomly(): Rotate = this.apply { rotateBy = Angle.Random }

    private enum class Angle {
        Random, Ninety, FortyFive
    }

}
