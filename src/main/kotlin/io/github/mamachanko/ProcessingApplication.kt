//package io.github.mamachanko
//
//import processing.core.PApplet
//import java.util.*
//
//class ProcessingApplication : PApplet() {
//
//    companion object {
//        val WIDTH = 800
//        val HEIGHT = 800
//        val BLACK = 0
//        val WHITE = 255
//        val GRID = Grid(2, 3)
//        val LAYOUT = Layout(50, 75, 15)
//    }
//
//    override fun settings(): Unit {
//        size(WIDTH, HEIGHT)
//    }
//
//    override fun setup(): Unit {
//        background(WHITE)
//        noStroke()
//        frameRate((4).toFloat())
//    }
//
//    override fun draw() {
//        defaults()
////        grays()
////        threeColors()
////        details()
//    }
//
//    private fun defaults() {
//        fill(WHITE)
//        rect(0.toFloat(), 0.toFloat(), WIDTH.toFloat(), HEIGHT.toFloat())
//        val page = Page(WIDTH, HEIGHT, LAYOUT, GRID)
//
//        page.shapes.forEach { shape ->
//            val piece = shape.slice().toList()[Random().nextInt(2)]
//            val color = piece.color
//            fill(color.red.toFloat(), color.green.toFloat(), color.blue.toFloat())
//            beginShape()
//            piece.getSortedVertices().forEach { vertex ->
//                vertex(vertex.x.toFloat(), vertex.y.toFloat())
//            }
//            endShape()
//        }
//    }
//
//    private fun grays() {
//        fill(WHITE)
//        rect(0.toFloat(), 0.toFloat(), WIDTH.toFloat(), HEIGHT.toFloat())
//
//        val page = Page(WIDTH, HEIGHT, LAYOUT, GRID)
//
//        page.tiles.forEach { tile ->
//            (0..1).forEach { i ->
//                val piece = tile.shapes.first().slice().toList()[Random().nextInt(2)]
//
//                fill(100 + 100 * i)
//                beginShape()
//                piece.getSortedVertices().forEach { vertex ->
//                    vertex(vertex.x.toFloat(), vertex.y.toFloat())
//                }
//                endShape()
//            }
//
//        }
//    }
//
//    private fun threeColors() {
//        fill(WHITE)
//        rect(0.toFloat(), 0.toFloat(), WIDTH.toFloat(), HEIGHT.toFloat())
//
//        val page = Page(WIDTH, HEIGHT, LAYOUT, GRID)
//
//        page.tiles.forEach { tile ->
//            val RED = Triple(253, 69, 59)
//            val BLUE = Triple(64, 108, 178)
//            val YELLOW = Triple(255, 193, 151)
//
//            val colors = listOf(RED, BLUE, YELLOW)
//
//            (0..2).forEach { i ->
//                val piece = tile.shapes.first().slice().toList()[Random().nextInt(2)]
//
//                val color = colors[Random().nextInt(colors.size)]
//                fill(color.first.toFloat(), color.second.toFloat(), color.third.toFloat())
//                beginShape()
//                piece.getSortedVertices().forEach { vertex ->
//                    vertex(vertex.x.toFloat(), vertex.y.toFloat())
//                }
//                endShape()
//            }
//
//        }
//    }
//
//    private fun details() {
//        fill(WHITE)
//        rect(0.toFloat(), 0.toFloat(), WIDTH.toFloat(), HEIGHT.toFloat())
//
//        val page = Page(WIDTH, HEIGHT, LAYOUT, Grid(1, 1))
//
//        page.tiles.forEach { tile ->
//            var shapes = tile.shapes
//
//            (0..5).forEach {
//                shapes = shapes.map { it.slice().toList() }.flatten()
//            }
//
//            shapes.forEachIndexed { i, shape ->
//                if (Math.random() < .25) {
//                    fill(((256.0 / shapes.size) * i).toInt())
//                    beginShape()
//                    shape.getSortedVertices().forEach { vertex ->
//                        vertex(vertex.x.toFloat(), vertex.y.toFloat())
//                    }
//                    endShape()
//                }
//            }
//
//        }
//    }
//
//    fun run(args: Array<String>): Unit {
//        PApplet.main("io.github.mamachanko.ProcessingApplication", args)
//    }
//}
//
//fun main(args: Array<String>): Unit = ProcessingApplication().run(args)