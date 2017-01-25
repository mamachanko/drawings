package io.github.mamachanko

import java.util.*
import kotlin.comparisons.compareBy

open class Shape() {

    companion object {
        val NO_VERTICES = emptySet<Vertex>()
    }

    var vertices: Set<Vertex> = NO_VERTICES
    var color: Color = Color(0, 0, 0, 1.0)

    constructor(vertices: Set<Vertex>) : this() {
        this.vertices = vertices
    }

    constructor(vertices: Set<Vertex>, color: Color) : this() {
        this.vertices = vertices
        this.color = color
    }

    fun getSortedVertices(): List<Vertex> {
        val averageVertex = getAverageVertex()
        return vertices.sortedWith(compareBy { averageVertex.polarDistanceTo(it) })
    }

    private fun getAverageVertex(): Vertex {
        val vertexSum = vertices.reduce { currentVertexSum, nextVertex ->
            Vertex(currentVertexSum.x + nextVertex.x, currentVertexSum.y + nextVertex.y)
        }
        return Vertex(vertexSum.x / vertices.size, vertexSum.y / vertices.size)
    }

    fun slice(): Pair<Shape, Shape> {

        val vertices = getSortedVertices()

        val indices = vertices.indices.minus(0).toMutableList()

        val randomIndices = listOf(
                indices.removeAt(Random().nextInt(indices.size)),
                indices.removeAt(Random().nextInt(indices.size))
        ).sorted()
        val indexX = randomIndices[0]
        val indexY = randomIndices[1]

        val head = vertices.subList(0, indexX)
        val body = vertices.subList(indexX, indexY)
        val tail = vertices.subList(indexY, vertices.lastIndex + 1)

        val vertexX = getVertexBetween(head.last(), body.first())
        val vertexY = getVertexBetween(body.last(), tail.first())

        val pieceOne = head.plus(tail).plus(vertexX).plus(vertexY)
        val pieceTwo = body.plus(vertexX).plus(vertexY)

        return Pair(Shape(pieceOne.toSet()), Shape(pieceTwo.toSet()))
    }

    private fun getVertexBetween(a: Vertex, b: Vertex): Vertex {
        val newDistance = Math.random()
        val x = (1 - newDistance) * a.x + newDistance * b.x
        val y = (1 - newDistance) * a.y + newDistance * b.y
        return Vertex(x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Shape

        if (vertices != other.vertices) return false

        return true
    }

    override fun hashCode(): Int {
        return vertices.hashCode()
    }

}

data class Vertex(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }

    fun distanceTo(otherVertex: Vertex): Double {
        return Math.sqrt(Math.pow(otherVertex.x - x, 2.0) + Math.pow(otherVertex.y - y, 2.0))
    }
}