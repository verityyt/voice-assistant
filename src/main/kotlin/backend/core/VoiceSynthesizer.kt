package backend.core

import org.openqa.selenium.By

object VoiceSynthesizer {

    fun speakText(text: String) {

        Logger.debug("Speaking now => '$text'",this.javaClass.name)

        val shutdown = text.endsWith("[shutdown]")
        val enterText = if(shutdown) {
            text.replace("[shutdown]","")
        }else {
            text
        }

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