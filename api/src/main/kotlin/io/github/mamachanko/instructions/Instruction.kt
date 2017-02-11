package io.github.mamachanko.instructions

abstract class Instruction(val prior: List<Instruction> = emptyList()) {

    fun add(): Add = Add(prior.plus(this))

    fun discard(): Discard = Discard(prior.plus(this))

    fun duplicate(): Duplicate = Duplicate(prior.plus(this))

    fun colorise(): Colorise = Colorise(prior.plus(this))

    fun slice(): Slice = Slice(prior.plus(this))

    fun shave(): Shave = Shave(prior.plus(this))

    fun then(): Instruction = this

    fun asList(): List<Instruction> = prior.plus(this)

    abstract fun applyTo(state: Drawing): Drawing
}