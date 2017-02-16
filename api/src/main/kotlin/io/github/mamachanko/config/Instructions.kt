package io.github.mamachanko.config

import io.github.mamachanko.instructions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Instructions {

    @Bean
    fun baseline(): Instruction = StartBy()
            .adding(12).rectangles().to(aGridOf(2 x 3).withCollapsedMargin(2.0))
            .then()
            .shave().all().randomly()
            .then()
            .colorise().all()

    @Bean
    fun baselineHalf(): Instruction = StartBy()
            .adding().rectangles().to(aGridOf(3 x 5).withCollapsedMargin(2.0))
            .then()
            .slice().all().withHalfProportions()
            .then()
            .colorise().all()

    @Bean
    fun baselineDoubleHalf(): Instruction = StartBy()
            .adding().rectangles().to(aGridOf(3 x 5).withCollapsedMargin(2.0))
            .then()
            .slice().all().withHalfProportions()
            .then()
            .slice().all().withHalfProportions()
            .then()
            .colorise().all()

    @Bean
    fun singleQuadroHalf(): Instruction = StartBy()
            .adding().a().rectangle()
            .then()
            .slice().all().withHalfProportions()
            .then()
            .slice().all().withHalfProportions()
            .then()
            .slice().all().withHalfProportions()
            .then()
            .slice().all().withHalfProportions()
            .then()
            .colorise().all()

    @Bean
    fun random(): Instruction = StartBy()
            .adding(6).rectangles().randomly()
            .then()
            .colorise().all()
}