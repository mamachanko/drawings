package io.github.mamachanko.geometry

data class Edge(val a: Vertex, val b: Vertex)

val List<Edge>.northAndSouth: Pair<Edge, Edge>
    get() {
        val centre = flatMap { listOf(it.a, it.b) }.toSet().average
        val verticalNormal = Edge(centre, Vertex(centre.x, centre.y + 1.0))
        val isCrossedByVertical: (Edge) -> Boolean = {
            orientationOf(verticalNormal.a, verticalNormal.b, it.a) !=
                    orientationOf(verticalNormal.a, verticalNormal.b, it.b)
        }
        val edgesCrossedByVertical = filter(isCrossedByVertical)
        return edgesCrossedByVertical.first() to edgesCrossedByVertical.last()
    }

