package io.github.mamachanko

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DrawingConfig {

    @Bean
    fun pageTemplate(): PageTemplate {
        return PageTemplate(Layout(125, 50, 50), Grid(2, 3))
    }

    @Bean
    fun colorPalette(): Palette {
        val red = Color(253, 63, 51, 100)
        val blue = Color(67, 105, 178, 100)
        val yellow = Color(255, 193, 58, 100)
        return ColorPalette(red, blue, yellow)
    }

//    @Bean
//    fun sliceOnceStrategy(): Strategy {
//        return SliceOnceStrategy()
//    }

    @Bean
    fun sliceOnceAndKeepOne(): Strategy {
        return DuplicateSliceAndKeepOneStrategy()
    }

}