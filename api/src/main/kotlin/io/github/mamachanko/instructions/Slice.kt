package io.github.mamachanko.instructions

import io.github.mamachanko.anyOne
import io.github.mamachanko.geometry.*
import io.github.mamachanko.oneOf
import java.util.*

open class Slice(priorInstructions: List<Instruction> = emptyList()) : Instruction(priorInstructions = priorInstructions) {

    private var vertical: Boolean = false
    private var proportions: Proportions = Proportions.Random

    fun all(): Slice {
        return this
    }

    fun randomly(): Slice {
        return this
    }

    override fun applyTo(drawing: Drawing): Drawing {
        val shapes = drawing.shapes.map { slice(it) }.flatMap { it }
        return drawing.withShapes(shapes)
    }

    fun slice(shape: Shape): List<Shape> {
        val (edge1, edge2) = if (vertical) {
            shape.chain.northAndSouth
        } else {
            shape.chain.randomlyChooseTwoInOrder()
        }
        val (edge1split1, edge1split2) = edge1.splitRandomly()
        val (edge2split1, edge2split2) = edge2.splitRandomly()
        return shape.chain
                .replace(edge1, edge1split1 to edge1split2)
                .replace(edge2, edge2split1 to edge2split2)
                .partitionIntoShapesBy(edge1split2, edge2split1)
                .toList()
                .map { it ->
                    it.toShape()
                }
    }

    private fun List<Edge>.randomlyChooseTwoInOrder(): Pair<Edge, Edge> {
        val edgeIndices = indices.toMutableList()
        val random = Random()
        val randomSortedIndices = listOf(
                edgeIndices.removeAt(random.nextInt(edgeIndices.size)),
                edgeIndices[random.nextInt(edgeIndices.size)]
        ).sorted()
        return this[randomSortedIndices[0]] to this[randomSortedIndices[1]]
    }

    private fun Edge.splitRandomly(): Pair<Edge, Edge> {
        val newDistance = when (proportions) {
            Proportions.Random -> Math.random()
            Proportions.Half -> .5
            Proportions.Golden -> listOf(.618, 1 - .618).anyOne()
        }
        val x = (1 - newDistance) * a.x + newDistance * b.x
        val y = (1 - newDistance) * a.y + newDistance * b.y
        val splitAt = Vertex(x, y)
        return Edge(a, splitAt) to Edge(splitAt, b)
    }

    private fun List<Edge>.replace(toBeReplaced: Edge, replaceWith: Pair<Edge, Edge>): List<Edge> {
        return map {
            if (it.equals(toBeReplaced)) {
                listOf(replaceWith.first, replaceWith.second)
            } else {
                listOf(it)
            }
        }.flatMap { it }
    }

    private fun List<Edge>.partitionIntoShapesBy(shapeStart: Edge, shapeEnd: Edge): Pair<List<Edge>, List<Edge>> {
        return partition { indexOf(shapeStart) <= indexOf(it) && indexOf(it) <= indexOf(shapeEnd) }
    }

    private fun List<Edge>.toShape(): Shape = Shape(map { it.vertices }.flatMap { it }.toSet())

    private val Edge.vertices: List<Vertex>
        get() = listOf(a, b)

    fun withHalfProportions(): Slice {
        this.proportions = Proportions.Half
        return this
    }

    fun vertically(): Slice {
        this.vertical = true
        return this
    }

    fun withGoldenProportions(): Slice {
        this.proportions = Proportions.Golden
        return this
    }

}

private enum class Proportions { Random, Half, Golden }
