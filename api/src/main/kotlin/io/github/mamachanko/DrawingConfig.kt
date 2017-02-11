package io.github.mamachanko

import io.github.mamachanko.instructions.Instruction
import io.github.mamachanko.instructions.StartBy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DrawingConfig {

    @Bean
    fun pageTemplate(): PageTemplate {
        return PageTemplate(Layout(10.0, 10.0, .0), Grid(3, 5))
    }

    @Bean
    fun colorPalette(): Palette {
        val red = Color(253, 63, 51, 100)
        val blue = Color(67, 105, 178, 100)
        val yellow = Color(255, 193, 58, 100)
        return ColorPalette(red, blue, yellow)
    }

    @Bean
    fun sliceOnceAndKeepOne(): Strategy {
        return DuplicateSliceAndKeepOneStrategy()
    }

    @Bean
    fun baselineInstructions(colorPalette: Palette): List<Instruction> {
        return StartBy()
                .adding().rectangles().inAGridOf(2, 3).withACollapsedMarginOf(10.0)
                .then()
                .duplicate().all()
                .then()
                .shave().all().randomly()
                .then()
                .colorise().all().from(colorPalette).asList()

    }

}