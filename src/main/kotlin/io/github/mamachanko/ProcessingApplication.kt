package io.github.mamachanko

import processing.core.PApplet

class ProcessingApplication : PApplet() {

    fun run(args : Array<String>) : Unit {
        PApplet.main("io.github.mamachanko.ProcessingApplication", args)
    }
}

fun main(args: Array<String>) : Unit = ProcessingApplication().run(args)