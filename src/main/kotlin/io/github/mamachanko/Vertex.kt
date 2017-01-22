package io.github.mamachanko

data class Vertex(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }

    fun distanceTo(otherVertex: Vertex): Double {
        return Math.sqrt(Math.pow(otherVertex.x - x, 2.0) + Math.pow(otherVertex.y - y, 2.0))
    }
}