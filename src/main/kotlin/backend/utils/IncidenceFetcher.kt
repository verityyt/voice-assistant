package backend.utils

import org.openqa.selenium.By
import java.util.ArrayList

object IncidenceFetcher {

    fun getIncidence(): Double {
        VoiceAssistant.driver.executeScript("window.open('','_blank');")
        VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[1])
        VoiceAssistant.driver.get(VoiceAssistant.coronaUrl)

        val cards = VoiceAssistant.driver.findElementsByClassName("card-body")

        for(card in cards) {
            val title = card.findElement(By.className("card-title"))
            val text = card.findElement(By.className("card-text"))

            if(text.text.startsWith("Neuinfektionen")) {
                return title.findElement(By.tagName("b")).text.replace(",",".").toDouble()
            }
        }

        VoiceAssistant.driver.close()

        return 0.0
    }

}