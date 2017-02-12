package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import io.github.mamachanko.color.Color
import io.github.mamachanko.color.ColorPalette
import io.github.mamachanko.color.SOLID
import io.github.mamachanko.color.SOLID_BLACK
import io.github.mamachanko.config.PaletteConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class PalettesIntegrationTest {

    @Autowired
    lateinit var paletteConfig: PaletteConfig

    @Test
    fun `should provide palettes from config file`() {
        assertThat(paletteConfig.colorPalettes()).containsExactly(
                ColorPalette(
                        Color(0, 167, 225, SOLID),
                        Color(0, 23, 31, SOLID),
                        Color(0, 52, 89, SOLID)
                ),
                ColorPalette(
                        SOLID_BLACK,
                        Color(255, 255, 255, SOLID)
                )
        )
    }
}
