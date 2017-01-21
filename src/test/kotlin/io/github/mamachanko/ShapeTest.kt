package io.github.mamachanko

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy

val topLeft = Vertex(.0, .0)
val topRight = Vertex(1.0, .0)
val bottomLeft = Vertex(.0, 1.0)
val bottomRight = Vertex(1.0, 1.0)

val topLeftToTopRight = Edge(topLeft, topRight)
val topRightToBottomRight = Edge(topRight, bottomRight)
val bottomRightToBottomLeft = Edge(bottomRight, bottomLeft)
val bottomLeftToTopLeft = Edge(bottomLeft, topLeft)

class EdgeTest {

    @Test
    fun `should return true if same direction and vertices as other`() {
        assertThat(Edge(topLeft, topRight)).isEqualTo(Edge(topLeft, topRight))
    }

    @Test
    fun `should return false if not same direction and vertices as other`() {
        assertThat(Edge(topLeft, topRight)).isNotEqualTo(Edge(topRight, topLeft))
    }

    @Test
    fun `should return true if inversion of other`() {
        assertThat(Edge(topLeft, topRight).inversionOf(Edge(topLeft, topRight))).isTrue()
        assertThat(Edge(topLeft, topRight).inversionOf(Edge(topRight, topLeft))).isTrue()
    }

    @Test
    fun `should return false if inversion of other`() {
        assertThat(Edge(topLeft, topRight).inversionOf(Edge(topLeft, bottomLeft))).isFalse()
        assertThat(Edge(topLeft, topRight).inversionOf(Edge(bottomLeft, topLeft))).isFalse()
    }
}

class ShapeTest {

    @Test
    fun splitLists() {
        val vertices = listOf("a", "b", "c", "d", "e", "f")

        val index1 = 1
        val index2 = 5

        assertThat(vertices).containsExactly("a", "b", "c", "d", "e", "f")
        assertThat(vertices.subList(0, index1)).containsExactly("a")
        assertThat(vertices.subList(index1, index2)).containsExactly("b", "c", "d", "e")
        assertThat(vertices.subList(index2, vertices.lastIndex + 1)).containsExactly("f")
    }

    @Test
    fun `should return sorted vertices as open convex path`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        assertThat(shape.getSortedVertices()).containsExactly(topLeft, topRight, bottomRight, bottomLeft)
    }

    @Test
    fun `should return edges in order as closed convex polygon`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        assertThat(shape.getSortedEdges()).containsExactly(
                topLeftToTopRight, topRightToBottomRight, bottomRightToBottomLeft, bottomLeftToTopLeft)
    }

    @Test
    fun `should return two resulting shapes when sliced across edges`() {
        val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))
        val pieces = shape.sliceAcrossEdges(topLeftToTopRight, .75, bottomRightToBottomLeft, .25)

        val betweenTops = Vertex(.0, .75)
        val betweenBottoms = Vertex(1.0, .75)

        assertThat(pieces.first.getSortedEdges()).containsExactly(
                Edge(topLeft, betweenTops),
                Edge(betweenTops, betweenBottoms),
                Edge(betweenBottoms, bottomLeft),
                bottomLeftToTopLeft)

        assertThat(pieces.second.getSortedEdges()).containsExactly(
                Edge(betweenTops, topRight),
                topRightToBottomRight,
                Edge(bottomRight, betweenBottoms),
                Edge(betweenBottoms, betweenTops))

        assertThat(pieces.first.edges).hasSize(4)
        assertThat(pieces.second.edges).hasSize(4)
        assertThat(pieces.first.vertices + pieces.second.vertices).hasSize(6)

        val newEdges = pieces.first.edges.intersect(pieces.second.edges)
        assertThat(newEdges).hasSize(1)
        assertThat(shape.edges).doesNotContain(newEdges.first())
    }

    @Test
    fun `should return resulting two shapes when randomly sliced across edges`() {
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

    val edges: Set<Edge> = getSortedEdges().toSet()

    fun sliceAcrossEdges(edge1: Edge, distance1: Double, edge2: Edge, distance2: Double): Pair<Shape, Shape> {

//        val x =

        return Pair(Shape(emptySet()), Shape(emptySet()))
    }
}

data class Vertex(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }
}

data class Edge(val a: Vertex, val b: Vertex) {

//    override fun equals(other: Any?): Boolean {
//
//    }

    fun inversionOf(other: Edge): Boolean {
        if (other?.javaClass != javaClass) return false
        other as Edge
        return setOf(a, b).equals(setOf(other.a, other.b))
    }
}