package io.github.mamachanko.geometry

import kotlin.comparisons.compareBy

val Set<Vertex>.convexHull: Set<Vertex>
    get() = generateSequence(findNextOnConvexHull(leftMost)) {
        if (it == leftMost) null else findNextOnConvexHull(it)
    }.toSet()

private val Set<Vertex>.leftMost: Vertex
    get() = sortedWith(compareBy { it.x }).first()

private fun Set<Vertex>.findNextOnConvexHull(from: Vertex): Vertex = this.minus(from).filter { potentialNext ->
    this.minus(potentialNext).filter { anyOther ->
        orientationOf(from, potentialNext, anyOther) != Orientation.COUNTERCLOCKWISE
    }.size == this.size - 1
}.maxWith(compareBy { from.distanceTo(it) })!!

private fun Vertex.distanceTo(other: Vertex): Double {
    return Math.sqrt(Math.pow(this.x - other.x, 2.0) + Math.pow(this.y - other.y, 2.0))
}

enum class Orientation {
    CLOCKWISE, COUNTERCLOCKWISE, COLINEAR
}

fun orientationOf(a: Vertex, b: Vertex, c: Vertex): Orientation {
    val orientation = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y)
    return when {
        orientation < 0 -> Orientation.CLOCKWISE
        orientation > 0 -> Orientation.COUNTERCLOCKWISE
        else -> Orientation.COLINEAR
    }
}