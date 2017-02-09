package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.assertj.core.error.ShouldHave
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy

class InstructionsTests {

    @Test
    fun `should create drawing from list of instructions`() {
        val instructions = StartBy()
                .adding().a().rectangle().fillingThePage()
                .then()
                .add().two().rectangles().fillingThePage()
                .then()
                .discard().one()
                .then()
                .duplicate().all()
                .then()
                .colorise().half().from(GrayPalette())

        val drawing = GivenABlank()
                .withWidth(600.0).and().withHeight(800.0)
                .follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(4)
        drawing.shapes.map {
            assertThat(it.vertices).containsExactly(
                    Vertex2(.0, .0), Vertex2(600.0, .0), Vertex2(600.0, 800.0), Vertex2(.0, 800.0)
            )
        }
        assertThat(drawing.shapes.filter { it.color.equals(SOLID_BLACK) }).hasSize(2)
        assertThat(drawing.shapes.filter { !it.color.equals(SOLID_BLACK) }).hasSize(2)
    }

    @Test
    fun `should create the baseline Kelleybert drawing from list of instructions`() {
        val color1 = Color(10, 20, 30, SOLID)
        val color2 = Color(40, 50, 60, SOLID)
        val instructions = StartBy()
                .adding().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(20.0)
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all().from(ColorPalette(color1, color2))

        val drawing = GivenABlank().withWidth(460.0).and().withHeight(380.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(12)
        assertThat(drawing.shapes.map { it.color }.toSet()).doesNotContain(SOLID_BLACK)
        assertThat(drawing.shapes.map { it.color }.toSet()).containsExactly(color1, color2)
    }

    @Test
    fun `should be able to slice shapes`() {

        val instructions = Add().one().rectangle().then().slice().randomly()

        val drawing = GivenABlank().withWidth(100.0).and().withHeight(100.0).follow(instructions.asList())

        assertThat(drawing.shapes).hasSize(2)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }).hasSize(8)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).hasSize(6)
        assertThat(drawing.shapes.map { it.vertices }.flatMap { it }.toSet()).containsAllOf(Vertex2(.0, .0), Vertex2(100.0, .0), Vertex2(100.0, 100.0), Vertex2(.0, 100.0))
        val vertices1 = drawing.shapes[0].vertices
        val vertices2 = drawing.shapes[1].vertices
        assertThat(vertices1.intersect(vertices2)).hasSize(2)
    }

}

class StartBy {
    fun adding(): Add = Add()
}

fun GivenABlank(): Drawing2 {
    return Drawing2()
}

data class Drawing2(val width: Double = .0, val height: Double = .0, val shapes: List<Shape2> = emptyList()) {

    fun plusShapes(shapes: List<Shape2>): Drawing2 {
        return this.copy(shapes = this.shapes.plus(shapes))
    }

    fun follow(instructions: List<Instruction>): Drawing2 {
        return instructions.fold(this, { state, instruction -> instruction.applyTo(state) })
    }

    fun withWidth(width: Double): Drawing2 {
        return this.copy(width = width)
    }

    fun withHeight(height: Double): Drawing2 {
        return this.copy(height = height)
    }

    fun withShapes(shapes: List<Shape2>): Drawing2 {
        return this.copy(shapes = shapes)
    }

    fun and(): Drawing2 {
        return this
    }

}

data class Shape2(val vertices: List<Vertex2> = emptyList(), val color: Color = SOLID_BLACK) {

    fun withVertices(vararg vertices: Vertex2): Shape2 {
        return this.copy(vertices = vertices.asList())
    }

    fun withColour(color: Color): Shape2 {
        return this.copy(color = color)
    }

    fun getSortedVertices(): List<Vertex2> {
        val averageVertex = getAverageVertex()
        return vertices.sortedWith(compareBy { averageVertex.polarDistanceTo(it) })
    }

    fun getSortedEdges(): List<Pair<Vertex2, Vertex2>> {
        val sortedVertices = getSortedVertices()
        val vertices = sortedVertices.plus(sortedVertices.first())
        return vertices.zip(vertices.drop(1))
    }

    private fun getAverageVertex(): Vertex2 {
        val vertexSum = vertices.reduce { currentVertexSum, nextVertex ->
            Vertex2(currentVertexSum.x + nextVertex.x, currentVertexSum.y + nextVertex.y)
        }
        return Vertex2(vertexSum.x / vertices.size, vertexSum.y / vertices.size)
    }
}

data class Vertex2(val x: Double, val y: Double) {

    fun polarDistanceTo(otherVertex: Vertex2): Double {
        return Math.atan2(otherVertex.y - y, otherVertex.x - x)
    }

}

abstract class Instruction(val prior: List<Instruction> = emptyList()) {

    fun add(): Add {
        return Add(prior.plus(this))
    }

    fun discard(): Discard {
        return Discard(prior.plus(this))
    }

    fun duplicate(): Duplicate {
        return Duplicate(prior.plus(this))
    }

    fun colorise(): Colorise {
        return Colorise(prior.plus(this))
    }

    fun slice(): Slice {
        return Slice(prior.plus(this))
    }

    fun  shave(): Shave {
        return Shave(prior.plus(this))
    }

    fun then(): Instruction {
        return this
    }

    fun asList(): List<Instruction> {
        return prior.plus(this)
    }
    abstract fun applyTo(state: Drawing2): Drawing2
}

class Add(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        val width = state.width
        val height = state.height
        val shape = Shape2().withVertices(
                Vertex2(.0, .0),
                Vertex2(width, .0),
                Vertex2(width, height),
                Vertex2(.0, height)
        )
        return state.plusShapes((1..count).map { shape })
    }

    private var count: Int = 0

    fun a(): Add {
        count = 1
        return this
    }

    fun two(): Add {
        count = 2
        return this
    }

    fun rectangle(): Add {
        return this
    }

    fun fillingThePage(): Add {
        return this
    }

    fun rectangles(): Add {
        return this
    }

    fun inAGridOf(rows: Int, columns: Int): Add {
        this.count = rows * columns
        return this
    }

    fun withACollapsedMarginOf(collapsedMargin: Double): Add {
        return this
    }

    fun one(): Add {
        return a()
    }

}

class Discard(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.withShapes(state.shapes.drop(1))
    }

    fun one(): Discard {
        return this
    }

}

class Duplicate(prior: List<Instruction> = emptyList()) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        return state.plusShapes(state.shapes)
    }

    fun all(): Duplicate {
        return this
    }

}

class Colorise(prior: List<Instruction>) : Instruction(prior) {

    private var palette: Palette = BlackPalette()

    private var percent: Int = 100

    override fun applyTo(state: Drawing2): Drawing2 {
        val secondHalf = state.shapes.subList(state.shapes.size / (100 / percent), state.shapes.size)
        val firstHalf = state.shapes.subList(0, state.shapes.size / (100 / percent)).map { shape -> shape.withColour(palette.color) }
        return state.withShapes(firstHalf.plus(secondHalf))
    }

    fun from(palette: Palette): Colorise {
        this.palette = palette
        return this
    }

    fun half(): Colorise {
        this.percent = 50
        return this
    }

    fun all(): Colorise {
        this.percent = 100
        return this
    }

}

open class Slice(prior: List<Instruction>) : Instruction(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        val slicesShapes = state.shapes.map { slice(it) }
        println("slice result:")
        println(slicesShapes)
        val shapes = slicesShapes.flatMap { it }
        println("flattened shapes")
        println(shapes)
        return state.withShapes(shapes)
//        return state.withShapes(slice(state.shapes.first()))
    }

    fun slice(shape: Shape2): List<Shape2> {
        // get sorted edges
        val edges = shape.getSortedEdges()
        println("####")
        println(edges)
        println("####")

        // pick two edges randomly
        val (firstEdge, secondEdge) = pickTwo(edges)

        println("####")
        println(edges)
        println("####")

        // insert new (randomly placed) vertices on these edges
        val (firstEdge1, firstEdge2) = sliceEdgeRandomly(firstEdge)
        val (secondEdge1, secondEdge2) = sliceEdgeRandomly(secondEdge)

        // replace the two edges with their split result
        val newEdges = edges.map {
            when {
                it.equals(firstEdge) -> listOf(firstEdge1, firstEdge2)
                it.equals(secondEdge) -> listOf(secondEdge1, secondEdge2)
                else -> listOf(it)
            }
        }.flatMap { it }

        println()
        println()
        println(newEdges)
        println()
        println()
        println()

        // partition the list of all edges into the two pieces
        val pieces = newEdges.partition { edge ->
            val edgeIndex = newEdges.indexOf(edge)
            val indexFirstEdge1 = newEdges.indexOf(firstEdge1)
            val indexSecondEdge2 = newEdges.indexOf(secondEdge2)
            val indices = listOf(indexFirstEdge1, indexSecondEdge2).sorted()
            if (edgeIndex > indices.first() && edgeIndex < indices.last()) {
                println("=> piece1: $edge")
                true
            } else {
                println("=> piece2: $edge")
                false
            }

        }

        println()
        println("pieces")
        println(pieces)

        // construct a shape of each piece
        val shape1 = Shape2(pieces.first.map { it.toList() }.flatMap { it }.toSet().toList())
        val shape2 = Shape2(pieces.second.map { it.toList() }.flatMap { it }.toSet().toList())

        if ((shape1.vertices.size + shape2.vertices.size) == 3) {
            println("stop")
        }

        // return
        println("sliced")
        println(shape)
        println("into")
        println(shape1)
        println(shape2)
        return listOf(shape1, shape2)
    }

    private fun sliceEdgeRandomly(edge: Pair<Vertex2, Vertex2>): Pair<Pair<Vertex2, Vertex2>, Pair<Vertex2, Vertex2>> {
        val (a, b) = edge
        val newDistance = Math.random()
        val x = (1 - newDistance) * a.x + newDistance * b.x
        val y = (1 - newDistance) * a.y + newDistance * b.y
        val new = Vertex2(x, y)
        println("split $edge into:")
        println(Pair(a, new))
        println("and")
        println(Pair(new, b))
        println("--")
        return Pair(a, new) to Pair(new, b)
    }

    private fun pickTwo(edges: List<Pair<Vertex2, Vertex2>>): Pair<Pair<Vertex2, Vertex2>, Pair<Vertex2, Vertex2>> {
        val indices = edges.indices.toMutableList()
        val random = Random()
        val indexFirstEdge = indices.removeAt(random.nextInt(indices.size))
        val indexSecondEdge = indices.removeAt(random.nextInt(indices.size))
        return edges[indexFirstEdge] to edges[indexSecondEdge]
    }

    fun all(): Slice {
        return this
    }

    fun randomly(): Slice {
        return this
    }

}

class Shave(prior: List<Instruction>) : Slice(prior) {

    override fun applyTo(state: Drawing2): Drawing2 {
        val slicesShapes = state.shapes.map { slice(it) }
        val shapes = slicesShapes.flatMap { it }
        return state.withShapes(listOf(shapes.first()))
    }
}
