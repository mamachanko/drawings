package io.github.mamachanko.geometry

import io.github.mamachanko.color.Color
import io.github.mamachanko.color.SOLID_BLACK
import kotlin.comparisons.compareBy

data class Shape(val vertices: Set<Vertex> = emptySet(), val color: Color = SOLID_BLACK) {

    val chain: List<Edge>
        get() {
            val closedPath = path.plus(path.first())
            return closedPath.zip(closedPath.drop(1)).map { Edge(it.first, it.second) }
        }

    val path: List<Vertex>
        get() = vertices.sortedWith(compareBy { average.polarDistanceTo(it) })

    private val average: Vertex
        get() {
            val vertexSum = vertices.reduce { currentVertexSum, nextVertex ->
                Vertex(currentVertexSum.x + nextVertex.x, currentVertexSum.y + nextVertex.y)
            }
            return Vertex(vertexSum.x / vertices.size, vertexSum.y / vertices.size)
        }

    fun withVertices(vararg vertices: Vertex): Shape {
        return this.copy(vertices = vertices.toSet())
    }

    fun withColour(color: Color): Shape {
        return this.copy(color = color)
    }
}