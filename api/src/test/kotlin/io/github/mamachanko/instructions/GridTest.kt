package io.github.mamachanko.instructions

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GridTest {

    @Test
    fun `should return 6 as the size of a 2 x 3 Grid`() {
        assertThat(Grid(2, 3).size).isEqualTo(6)
    }

    @Test
    fun `should return 8 as the size of a 1 x 8 Grid`() {
        assertThat(Grid(1, 8).size).isEqualTo(8)
    }

    @Test
    fun `should return 10k as the size of a 100 x 100 Grid`() {
        assertThat(Grid(100, 100).size).isEqualTo(10000)
    }

    @Test
    fun `should return column and row of its the n-th cell`() {
        assertThat(Grid(2, 3).indexOf(0).column).isEqualTo(0)
        assertThat(Grid(2, 3).indexOf(0).row).isEqualTo(0)

        assertThat(Grid(2, 3).indexOf(1).column).isEqualTo(1)
        assertThat(Grid(2, 3).indexOf(1).row).isEqualTo(0)

        assertThat(Grid(2, 3).indexOf(2).column).isEqualTo(0)
        assertThat(Grid(2, 3).indexOf(2).row).isEqualTo(1)

        assertThat(Grid(2, 3).indexOf(3).column).isEqualTo(1)
        assertThat(Grid(2, 3).indexOf(3).row).isEqualTo(1)

        assertThat(Grid(2, 3).indexOf(4).column).isEqualTo(0)
        assertThat(Grid(2, 3).indexOf(4).row).isEqualTo(2)

        assertThat(Grid(2, 3).indexOf(5).column).isEqualTo(1)
        assertThat(Grid(2, 3).indexOf(5).row).isEqualTo(2)
    }

    @Test
    fun `should return column and row of its the n-th cell wrapped`() {
        assertThat(Grid(2, 3).indexOf(6)).isEqualTo(GridIndex(0, 0))
        assertThat(Grid(2, 3).indexOf(14)).isEqualTo(GridIndex(0, 1))
        assertThat(Grid(2, 3).indexOf(23)).isEqualTo(GridIndex(1, 2))
        assertThat(Grid(2, 3).indexOf(24)).isEqualTo(GridIndex(0, 0))
    }
}