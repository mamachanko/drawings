package io.github.mamachanko

import java.util.*

fun oneOf(collection: Set<Any>): Any {
    return collection.toList()[Random().nextInt(collection.size)]
}

fun <T> Collection<T>.anyOne(): T {
    return toList()[Random().nextInt(size)]
}
