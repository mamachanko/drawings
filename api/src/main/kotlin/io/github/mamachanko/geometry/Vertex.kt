package io.github.mamachanko.geometry

data class Vertex(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }

}

val Set<Vertex>.average: Vertex
    get() {
        val vertexSum = reduce { currentVertexSum, nextVertex ->
            Vertex(currentVertexSum.x + nextVertex.x, currentVertexSum.y + nextVertex.y)
        }
        return Vertex(vertexSum.x / size, vertexSum.y / size)
    }
