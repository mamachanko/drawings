package io.github.mamachanko.instructions

import java.util.*

open class Slice(prior: List<Instruction>) : Instruction(prior) {

    fun all(): Slice {
        return this
    }

    fun randomly(): Slice {
        return this
    }

    override fun applyTo(state: Drawing2): Drawing2 {
        val slicesShapes = state.shapes.map { slice(it) }
        val shapes = slicesShapes.flatMap { it }
        return state.withShapes(shapes)
    }

    fun slice(shape: Shape2): List<Shape2> {
        val (edge1, edge2) = shape.chain.randomlyChooseTwoInOrder()
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

    private fun List<Edge2>.randomlyChooseTwoInOrder(): Pair<Edge2, Edge2> {
        val edgeIndices = indices.toMutableList()
        val random = Random()
        val randomSortedIndices = listOf(
                edgeIndices.removeAt(random.nextInt(edgeIndices.size)),
                edgeIndices[random.nextInt(edgeIndices.size)]
        ).sorted()
        return this[randomSortedIndices[0]] to this[randomSortedIndices[1]]
    }

    private fun Edge2.splitRandomly(): Pair<Edge2, Edge2> {
        val newDistance = Math.random()
        val x = (1 - newDistance) * a.x + newDistance * b.x
        val y = (1 - newDistance) * a.y + newDistance * b.y
        val splitAt = Vertex2(x, y)
        return Edge2(a, splitAt) to Edge2(splitAt, b)
    }

    private fun List<Edge2>.replace(toBeReplaced: Edge2, replaceWith: Pair<Edge2, Edge2>): List<Edge2> {
        return map {
            if (it.equals(toBeReplaced)) {
                listOf(replaceWith.first, replaceWith.second)
            } else {
                listOf(it)
            }
        }.flatMap { it }
    }

    private fun List<Edge2>.partitionIntoShapesBy(shapeStart: Edge2, shapeEnd: Edge2): Pair<List<Edge2>, List<Edge2>> {
        return partition { indexOf(shapeStart) <= indexOf(it) && indexOf(it) <= indexOf(shapeEnd) }
    }

    private fun List<Edge2>.toShape(): Shape2 = Shape2(map { it.vertices }.flatMap { it }.toSet())

    private val Edge2.vertices: List<Vertex2>
        get() = listOf(a, b)

}