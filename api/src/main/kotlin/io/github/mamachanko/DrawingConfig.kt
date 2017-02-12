package io.github.mamachanko

import io.github.mamachanko.color.Color
import io.github.mamachanko.color.ColorPalette
import io.github.mamachanko.color.Palette
import io.github.mamachanko.instructions.Instruction
import io.github.mamachanko.instructions.StartBy
import io.github.mamachanko.instructions.aGridOf
import io.github.mamachanko.instructions.x
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DrawingConfig {

    @Bean
    fun colorPalette(): Palette {
        val red = Color(253, 63, 51, 100)
        val blue = Color(67, 105, 178, 100)
        val yellow = Color(255, 193, 58, 100)
        return ColorPalette(red, blue, yellow)
    }

    @Bean
    fun instructions(colorPalette: Palette): List<Instruction> {
        return StartBy()
                .adding().rectangles().to(aGridOf(2 x 3).withCollapsedMargin(10.0))
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all().from(colorPalette).asList()

    }

}