package io.github.mamachanko.instructions

abstract class Instruction(val priorInstructions: List<Instruction> = emptyList()) {

    fun add(): Add = Add(priorInstructions = asList())

    fun add(count: Int): Add = Add(count = count, priorInstructions = asList())

    fun discard(): Discard = Discard(priorInstructions.plus(this))

    fun duplicate(): Duplicate = Duplicate(priorInstructions.plus(this))

    fun colorise(): Colorise = Colorise(priorInstructions.plus(this))

    fun slice(): Slice = Slice(priorInstructions.plus(this))

    fun shave(): Shave = Shave(priorInstructions.plus(this))

    fun rotate(): Rotate = Rotate(priorInstructions.plus(this))

    fun then(): Instruction = this

    fun asList(): List<Instruction> = priorInstructions.plus(this)
    abstract fun applyTo(drawing: Drawing): Drawing
}