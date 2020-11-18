package backend.core

import org.openqa.selenium.By

object VoiceSynthesizer {

    fun speakText(text: String) {

        val shutdown = text.endsWith("[shutdown]")
        val enterText = if(shutdown) {
            text.replace("[shutdown]","")
        }else {
            text
        }

        Jarvis.switchTab(1)

        val textarea = Jarvis.driver.findElement(By.id("voicetext"))
        textarea.clear()
        textarea.sendKeys(enterText)

        val playSound = Jarvis.driver.findElement(By.id("vorlesenbutton"))
        playSound.click()

        var playTime = 0L

        for(char in text) {
            playTime += 90
        }

        Thread.sleep(playTime)

        if(shutdown) {
            Jarvis.shutdown()
        }

    }

}