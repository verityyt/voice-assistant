package backend

import org.openqa.selenium.By

object VoiceSynthesizer {

    fun speakText(text: String) {

        val stop = text.endsWith("[stop]")
        val enterText = if(stop) {
            text.replace("[stop]","")
        }else {
            text
        }

        VoiceAssistant.switchTab(1)

        val textarea = VoiceAssistant.driver.findElement(By.id("voicetext"))
        textarea.clear()
        textarea.sendKeys(enterText)

        val playSound = VoiceAssistant.driver.findElement(By.id("vorlesenbutton"))
        playSound.click()

        var playTime = 0L

        for(char in text) {
            playTime += 90
        }

        Thread.sleep(playTime)

        if(stop) {
            VoiceAssistant.shutdown()
        }

    }

}