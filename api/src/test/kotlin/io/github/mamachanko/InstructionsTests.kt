package io.github.mamachanko

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InstructionsTests {

    @Test
    fun `should draw rectangle on page`() {
        val instructions = ToAnEmptyPage()
                .withWidth(500.0).and().withHeight(900.0)
                .add().a().rectangle().fillingThePage()
                .andSee()
        val drawing = createDrawingFrom(instructions)

        assertThat(drawing.shapes).hasSize(0)  // TODO: this is wrong. continue here.
    }

    private fun createDrawingFrom(instructions: Instructions): ADrawing {
        return ADrawing()
    }
}

class ToAnEmptyPage {
    var width: Double = .0
    var height: Double = .0

    fun withWidth(width: Double): ToAnEmptyPage {
        this.width = width
        return this
    }

    fun and(): ToAnEmptyPage {
        return this
    }

    fun withHeight(height: Double): ToAnEmptyPage {
        this.height = height
        return this
    }

    fun add(): Add {
        return Add()
    }


}

class Instructions {

}

class Add {
    var count: Int = 1

    fun  a(): Add {
        return this
    }

    fun rectangle(): Add {
        return this
    }

    fun fillingThePage(): Add {
        return this
    }

    fun andSee(): Instructions {
        return Instructions()
    }

}

class ADrawing {
    val shapes: List<AShape> = emptyList()
}

class AShape {

}
