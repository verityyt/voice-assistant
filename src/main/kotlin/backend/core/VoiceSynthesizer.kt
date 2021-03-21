package backend.core

import hud.HUD
import org.openqa.selenium.By
import java.util.*

object VoiceSynthesizer {

    private var latestOutput = ""

    fun speakText(text: String) {

        if(!VoiceRecognizer.offers.contains(text)) {
            latestOutput = text
        }

        VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[0])

        val shutdown = text.endsWith("[shutdown]")
        val enterText = if (shutdown) {
            text.replace("[shutdown]", "")
        } else {
            text
        }

        Logger.debug("Speaking now => '$enterText'", this.javaClass.name)

        val textarea = VoiceAssistant.driver.findElement(By.id("inputDiv"))
        textarea.clear()
        textarea.sendKeys(enterText)

        val playButton = VoiceAssistant.driver.findElement(By.className("btn-play"))
        playButton.click()


        HUD.play("speaking")

        while (true) {
            val playingButton = VoiceAssistant.driver.findElements(By.className("btn-play")).size

            if (playingButton == 1) {
                break
            }

            val textPreview = VoiceAssistant.driver.findElement(By.id("ccText"))
            if (textPreview.findElements(By.id("cc-w")).size > 0) {
                if (!HUD.isShown) {
                    HUD.show()
                }
            }
        }

        if (shutdown) {
            VoiceAssistant.shutdown()
        }

        HUD.hide()

    }

}