package io.github.mamachanko.config

import io.github.mamachanko.instructions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Instructions {

    @Bean
    fun baseline(): List<Instruction> {
        return StartBy()
                .adding(12).rectangles().to(aGridOf(2 x 3).withCollapsedMargin(2.0))
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all()
                .asList()
    }
}