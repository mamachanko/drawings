package io.github.mamachanko

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DrawingApplication

fun main(args: Array<String>) {
    SpringApplication.run(DrawingApplication::class.java, *args)
}
