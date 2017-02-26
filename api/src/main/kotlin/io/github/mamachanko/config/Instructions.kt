package io.github.mamachanko.config

import io.github.mamachanko.instructions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Instructions {

    @Bean
    fun baseline(): Instruction = StartBy()
            .adding(12).rectangles().to(aGridOf(2 x 3).withCollapsedMargin(20.0))
            .then()
            .shave().all().randomly()
            .then()
            .colorise().all()

    @Bean
    fun rotate(): Instruction = StartBy()
            .adding().rectangles().to(aGridOf(10 x 10))
            .then()
            .rotate().by().fortyFive().degrees()
            .then()
            .colorise().all()

    @Bean
    fun golden(): Instruction = StartBy()
            .adding().one().rectangle()
            .then()
            .slice().withGoldenProportions().vertically()
            .then()
            .slice().withGoldenProportions().vertically()
            .then()
            .slice().withGoldenProportions().vertically()
            .then()
            .colorise()

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

    @Bean
    fun vertical(): Instruction = StartBy()
            .adding().rectangles().to(aGridOf(1 x 3))
            .then()
            .slice().all().randomly()
            .then()
            .slice().all().vertically()
            .then()
            .colorise().all()

    @Bean
    fun plentyOfGoldenSlices(): Instruction = StartBy()
            .adding().one().rectangle()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .slice().all().withGoldenProportions()
            .then()
            .colorise().all()

}