package backend

import org.jsoup.Jsoup
import org.openqa.selenium.By
import java.awt.TextArea

object VoiceRecognition {

    fun startRecognition() {

        VoiceAssistant.driver.get("https://translate.google.com/?hl=de&ui=tob&sl=de&tl=en&op=translate")
        val microphoneButton = VoiceAssistant.driver.findElement(By.className("MtoyUd"))

        microphoneButton.click() // Starting recognition

        while(true) {
            if(VoiceAssistant.driver.pageSource.toLowerCase().contains("hey zeus")) {

                microphoneButton.click() // Stopping recognition

                val clearButton = VoiceAssistant.driver.findElement(By.className("GA2I6e"))
                clearButton.click() // Clearing textarea

                microphoneButton.click() // Restart recognition
                SoundManager.playSound("start")

                Thread.sleep(5000)
                microphoneButton.click() // Stop recognition
                SoundManager.playSound("end")

                val doc = Jsoup.parse(VoiceAssistant.driver.pageSource)
                val element = doc.getElementsByClass("D5aOJc").first()

                handleRecognition(element.text())

            }
        }

    }

    private fun handleRecognition(textArea: String) {

        println("Du hast '$textArea' gesagt!")

    }

}