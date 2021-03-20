package backend.utils

import org.jsoup.Jsoup
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.util.ArrayList

object YoutubeFetcher {

    fun getTop(query: String): String {
        val converted = query.replace(" ", "+")

        VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[0])
        VoiceAssistant.driver.executeScript("window.open('','_blank');")
        VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[1])
        VoiceAssistant.driver.get("https://www.youtube.com/results?search_query=$converted")

        Thread.sleep(500)

        val renderers = VoiceAssistant.driver.findElementsByClassName("ytd-item-section-renderer")
        var content: WebElement? = null

        for(renderer in renderers) {
            if(renderer.getAttribute("id") == "contents") {
                content = renderer
            }
        }

        val topResult = content!!.findElements(By.tagName("ytd-video-renderer")).first()
        val href = topResult.findElement(By.id("video-title")).getAttribute("href")

        VoiceAssistant.driver.close()

        return href
    }

}