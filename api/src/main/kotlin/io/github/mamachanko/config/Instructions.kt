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
}