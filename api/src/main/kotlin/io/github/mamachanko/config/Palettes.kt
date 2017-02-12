package io.github.mamachanko.config

import io.github.mamachanko.color.Color
import io.github.mamachanko.color.ColorPalette
import io.github.mamachanko.color.Palette
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "drawings")
class PaletteConfig {
    val palettes: List<List<String>> = arrayListOf()

    @Bean
    fun colorPalettes(): Set<Palette> {
        return palettes.map {
            it.map { Color.fromHex(it) }.toTypedArray()
        }.map { ColorPalette(*it) }.toSet()
    }
}
