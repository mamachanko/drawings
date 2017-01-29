package io.github.mamachanko.integration

import io.github.bonigarcia.wdm.ChromeDriverManager
import org.fluentlenium.adapter.junit.FluentTest
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.concurrent.TimeUnit


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KelleybertFeatureTests : FluentTest() {

    @LocalServerPort
    var randomServerPort: Int = 0

    val webDriver: ChromeDriver = ChromeDriver()

    companion object {
        @BeforeClass @JvmStatic
        fun setupClass() {
            ChromeDriverManager.getInstance().setup()
        }
    }

    @After
    fun teardown() {
        webDriver?.quit()
    }

    @Test
    fun `should show a non-blank canvas after some time`() {
        whenIVisitThePage()
        thenISeeANonBlankCanvasAfterSomeTime()
    }

    private fun whenIVisitThePage() {
        goTo("localhost:$randomServerPort")
    }

    private fun thenISeeANonBlankCanvasAfterSomeTime() {
        assertThat(`$`("#drawingCanvas"), hasSize(1))

        val blankCanvasContent = executeScript("return window.blankCanvasData").result.toString()
        assertThat(blankCanvasContent, containsString("data:image/png;base64"))

        val getDrawingCanvasContent = "return document.getElementById(\"drawingCanvas\").toDataURL();"
        await().atMost(5, TimeUnit.SECONDS).until {
            val drawingCanvasContent = executeScript(getDrawingCanvasContent).result.toString()
            !drawingCanvasContent.equals(blankCanvasContent)
        }
    }
}