package io.github.mamachanko.color

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ColorTests {

    @Test
    fun `should be equal if all values are equal`() {
        assertThat(Color(1, 2, 3, 4)).isEqualTo(Color(1, 2, 3, 4))
    }

    @Test
    fun `should not be equal if values differ`() {
        assertThat(Color(1, 2, 3, 4)).isNotEqualTo(Color(1, 2, 3, 5))
    }

    @Test
    fun `should parse Black from hex string description`() {
        assertThat(Color.fromHex("000000")).isEqualTo(SOLID_BLACK)
    }

    @Test
    fun `should parse White from hex string description`() {
        assertThat(Color.fromHex("FFFFFF")).isEqualTo(Color(255, 255, 255, SOLID))
    }

    @Test
    fun `should parse Blue from hex string description`() {
        assertThat(Color.fromHex("00A7E1")).isEqualTo(Color(0, 167, 225, SOLID))
        assertThat(Color.fromHex("00171F")).isEqualTo(Color(0, 23, 31, SOLID))
        assertThat(Color.fromHex("003459")).isEqualTo(Color(0, 52, 89, SOLID))
    }
}