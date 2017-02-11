package io.github.mamachanko.geometry

data class Vertex(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }

}