package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy

val topLeft = Vertex(.0, .0)
val topRight = Vertex(1.0, .0)
val bottomLeft = Vertex(.0, 1.0)
val bottomRight = Vertex(1.0, 1.0)

val top = Edge(topLeft, topRight)
val left = Edge(topLeft, bottomLeft)
val right = Edge(topRight, bottomRight)
val bottom = Edge(bottomLeft, bottomRight)

class EdgeTest {

    @Test
    fun `should return true when comparing Edges with vertices swapped`() {
        assertThat(Edge(topLeft, topRight)).isEqualTo(Edge(topRight, topLeft))
    }
}

class ShapeTest {

    @Test
    fun `should return sorted vertices as open convex path`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        assertThat(shape.getSortedVertices()).containsExactly(topLeft, topRight, bottomRight, bottomLeft)
    }

    @Test
    fun `should return edges in order as closed convex polygon`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        assertThat(shape.getSortedEdges()).containsExactly(top, right, bottom, left)
    }

    @Test
    fun `should return resulting shapes when randomly sliced in two across edges`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        val pieces = shape.randomlySliceAcrossEdges()

        val verticesOfPieces = pieces.first.vertices + pieces.second.vertices
        assertThat(verticesOfPieces).hasSize(6)

        val newEdges = pieces.first.edges.intersect(pieces.second.edges)
        assertThat(newEdges).hasSize(1)
        assertThat(shape.edges).doesNotContain(newEdges.first())
    }
}

class Shape(val vertices: Set<Vertex>) {

    fun getSortedVertices(): List<Vertex> {
        val averageVertex = getAverageVertex()
        return vertices.sortedWith(compareBy { averageVertex.polarDistanceTo(it) })
    }

    fun getSortedEdges(): List<Edge> {
        val sortedVertices = getSortedVertices().toMutableList()
        sortedVertices.add(sortedVertices.first())
        return sortedVertices.zip(sortedVertices.drop(1), { x, y -> Edge(x, y) })
    }

    private fun getAverageVertex(): Vertex {
        val vertexSum = vertices.reduce { currentVertexSum, nextVertex ->
            Vertex(currentVertexSum.x + nextVertex.x, currentVertexSum.y + nextVertex.y)
        }
        return Vertex(vertexSum.x / vertices.size, vertexSum.y / vertices.size)
    }

    fun randomlySliceAcrossEdges(): Pair<Shape, Shape> {
        val edges = edges.toMutableList()
        Collections.shuffle(edges)
        val a = edges.drop(1)
        val b = edges.drop(1)
        return Pair(Shape(emptySet()), Shape(emptySet()))
    }

    val edges: Set<Edge> = emptySet()  // setOf(getSortedEdges())
}

data class Vertex(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }
}

data class Edge(val a: Vertex, val b: Vertex) {

    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != javaClass) return false
        other as Edge
        return setOf(a, b).equals(setOf(other.a, other.b))
    }
}