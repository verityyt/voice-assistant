package backend

import org.openqa.selenium.By

object VoiceSynthesizer {

    fun speakText(text: String) {

        val stop = text.endsWith("[stop]")

        VoiceAssistant.driver.get("https://ttsmp3.com/text-to-speech/German/")

        Thread.sleep(1500)

        val textarea = VoiceAssistant.driver.findElement(By.id("voicetext"))
        textarea.sendKeys(text)

        val playSound = VoiceAssistant.driver.findElement(By.id("vorlesenbutton"))
        playSound.click()

        var playTime = 0L

        for(char in text) {
            playTime += 100
        }

        Thread.sleep(playTime)

        if(stop) {
            VoiceAssistant.shutdown()
        }

    }

}