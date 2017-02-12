package io.github.mamachanko.config

import io.github.mamachanko.instructions.Instruction
import io.github.mamachanko.instructions.StartBy
import io.github.mamachanko.instructions.aGridOf
import io.github.mamachanko.instructions.x
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Instructions {

    @Bean
    fun baseline(): List<Instruction> {
        return StartBy()
                .adding().rectangles().to(aGridOf(2 x 3).withCollapsedMargin(2.0))
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all()
                .asList()
    }

    @Bean
    fun riley(): List<Instruction> {
        return StartBy()
                .adding().rectangles().to(aGridOf(8 x 1).withCollapsedMargin(2.0))
                .colorise().all()
                .asList()
    }

    @Bean
    fun rothko(): List<Instruction> {
        return StartBy()
                .adding().rectangles().to(aGridOf(1 x 3).withCollapsedMargin(2.0))
                .colorise().all()
                .asList()
    }

    @Bean
    fun pixels(): List<Instruction> {
        return StartBy()
                .adding().rectangles().to(aGridOf(15 x 20).withCollapsedMargin(.0))
                .shave().all()
                .colorise().all()
                .asList()
    }
}