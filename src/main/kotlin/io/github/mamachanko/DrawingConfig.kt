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
        return GrayPalette()
    }

    @Bean
    fun creationStrategy(): Strategy {
        return IdempotenceStrategy()
    }

//    @Bean
//    fun sliceOnce(): Strategy {
//        return SliceOnceStrategy()
//    }

}