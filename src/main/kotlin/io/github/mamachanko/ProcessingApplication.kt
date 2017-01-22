package io.github.mamachanko

import processing.core.PApplet
import java.util.*

class ProcessingApplication : PApplet() {

    companion object {
        val WIDTH = 500
        val HEIGHT = 700
        val BLACK = 0
        val WHITE = 255
        val GRID = Grid(2, 3)
        val LAYOUT = Layout(50, 75, 15)
    }

    override fun settings(): Unit {
        size(WIDTH, HEIGHT)
    }

    override fun setup(): Unit {
        background(WHITE)
        noStroke()
//        noLoop()
        frameRate((1).toFloat())
    }

    override fun draw() {
        fill(WHITE)
        rect(0.toFloat(), 0.toFloat(), WIDTH.toFloat(), HEIGHT.toFloat())

        val page = Page(WIDTH, HEIGHT, LAYOUT, GRID)

        fill(BLACK)
        page.tiles.forEach { tile ->
            val topLeft = Vertex(tile.x.toDouble(), tile.y.toDouble())
            val topRight = Vertex(tile.x.toDouble() + tile.width.toDouble(), tile.y.toDouble())
            val bottomRight = Vertex(tile.x.toDouble() + tile.width.toDouble(), tile.y.toDouble() + tile.height.toDouble())
            val bottomLeft = Vertex(tile.x.toDouble(), tile.y.toDouble() + tile.height.toDouble())
            val shape = Shape(setOf(topLeft, topRight, bottomLeft, bottomRight))

            (0..1).forEach { i ->
                val piece = shape.slice().toList()[Random().nextInt(2)]

                fill(100 + 100 * i)
                beginShape()
                piece.getSortedVertices().forEach { vertex ->
                    vertex(vertex.x.toFloat(), vertex.y.toFloat())
                }
                endShape()
            }

        }
        save("/tmp/kelleybert_${frameCount}.tif")
    }

    fun run(args: Array<String>): Unit {
        PApplet.main("io.github.mamachanko.ProcessingApplication", args)
    }
}

fun main(args: Array<String>): Unit = ProcessingApplication().run(args)