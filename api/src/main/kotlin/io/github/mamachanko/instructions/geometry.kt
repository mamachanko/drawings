package io.github.mamachanko.instructions

import io.github.mamachanko.Color
import io.github.mamachanko.SOLID_BLACK
import kotlin.comparisons.compareBy

data class Shape2(val vertices: Set<Vertex2> = emptySet(), val color: Color = SOLID_BLACK) {

    val chain: List<Edge2>
        get() {
            val closedPath = path.plus(path.first())
            return closedPath.zip(closedPath.drop(1)).map { Edge2(it.first, it.second) }
        }

    val path: List<Vertex2>
        get() = vertices.sortedWith(compareBy { average.polarDistanceTo(it) })

    private val average: Vertex2
        get() {
            val vertexSum = vertices.reduce { currentVertexSum, nextVertex ->
                Vertex2(currentVertexSum.x + nextVertex.x, currentVertexSum.y + nextVertex.y)
            }
            return Vertex2(vertexSum.x / vertices.size, vertexSum.y / vertices.size)
        }

    fun withVertices(vararg vertices: Vertex2): Shape2 {
        return this.copy(vertices = vertices.toSet())
    }

    fun withColour(color: Color): Shape2 {
        return this.copy(color = color)
    }
}

data class Vertex2(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex2): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }

}

data class Edge2(val a: Vertex2, val b: Vertex2)
