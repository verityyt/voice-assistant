package backend.core

import org.openqa.selenium.By
import java.util.ArrayList

object VoiceSynthesizer {

    fun speakText(text: String) {

        VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[0])

        val shutdown = text.endsWith("[shutdown]")
        val enterText = if(shutdown) {
            text.replace("[shutdown]","")
        }else {
            text
        }

        Logger.debug("Speaking now => '$enterText'",this.javaClass.name)

        val textarea = VoiceAssistant.driver.findElement(By.id("voicetext"))
        textarea.clear()
        textarea.sendKeys(enterText)

        val playButton = VoiceAssistant.driver.findElement(By.id("vorlesenbutton"))
        playButton.click()

        while(true) {
            val value = playButton.getAttribute("value")

            if(value == "Read") {
                break
            }
        }

        if(shutdown) {
            VoiceAssistant.shutdown()
        }

    }

}