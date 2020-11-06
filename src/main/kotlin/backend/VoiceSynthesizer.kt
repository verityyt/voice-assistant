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

        VoiceAssistant.driver.get("https://translate.google.com/?hl=de&ui=tob&sl=de&tl=en&text=${enterText.replace(" ","%20")}%0A&op=translate")

        Thread.sleep(2000)

        val playSound = VoiceAssistant.driver.findElement(By.className("SSgGrd"))
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