package backend

import org.jsoup.Jsoup
import org.openqa.selenium.By

object VoiceRecognition {

    val greetings = listOf("hallo", "guten morgen", "guten tag", "guten abend", "servus", "Hi")
    val offers = listOf("Wie kann ich ihnen helfen?", "Kann ich irgendetwas für sie tun?", "Kann ich ihnen irgendwie behilflich sein?")

    fun startRecognition() {

        VoiceAssistant.driver.get("https://translate.google.com/?hl=de&ui=tob&sl=de&tl=en&op=translate")
        val microphoneButton = VoiceAssistant.driver.findElement(By.className("MtoyUd"))

        println("[VoiceRecognition] Starting general recognition for keyword")
        microphoneButton.click() // Starting recognition

        var success = false

        while(!success) {
            if(VoiceAssistant.driver.pageSource.toLowerCase().contains(VoiceAssistant.keyword)) {

                println("[VoiceRecognition] Recognized keyword, stopping general recognition")
                microphoneButton.click() // Stopping recognition

                val clearButton = VoiceAssistant.driver.findElement(By.className("GA2I6e"))
                clearButton.click() // Clearing textarea

                println("[VoiceRecognition] Starting specific recognition")
                microphoneButton.click() // Restart recognition
                SoundManager.playSound("start")

                while(!success) {
                    val firstDoc = Jsoup.parse(VoiceAssistant.driver.pageSource)
                    val firstElement = firstDoc.getElementsByClass("D5aOJc").first()

                     Thread.sleep(1500) // Checking if user stopped speaking within 1.5 seconds

                    val secondDoc = Jsoup.parse(VoiceAssistant.driver.pageSource)
                    val secondElement = secondDoc.getElementsByClass("D5aOJc").first()

                    if(firstElement == secondElement && firstElement.text() != "") {

                        println("[VoiceRecognition] Recognized stopped speaking, stopping specific recognition and handling input")
                        microphoneButton.click() // Stop recognition
                        SoundManager.playSound("end")

                        val doc = Jsoup.parse(VoiceAssistant.driver.pageSource)
                        val element = doc.getElementsByClass("D5aOJc").first()

                        handleRecognition(element.text())
                        success = true

                    }
                }
            }
        }

    }

    private fun startActiveRecognition() {

        VoiceAssistant.driver.get("https://translate.google.com/?hl=de&ui=tob&sl=de&tl=en&op=translate")
        val microphoneButton = VoiceAssistant.driver.findElement(By.className("MtoyUd"))

        println("[VoiceRecognition] Starting new active specific recognition")
        microphoneButton.click() // Starting recognition
        SoundManager.playSound("start")

        var success = false

        while(!success) {
            val firstDoc = Jsoup.parse(VoiceAssistant.driver.pageSource)
            val firstElement = firstDoc.getElementsByClass("D5aOJc").first()

            Thread.sleep(1500) // Checking if user stopped speaking within 1.5 seconds

            val secondDoc = Jsoup.parse(VoiceAssistant.driver.pageSource)
            val secondElement = secondDoc.getElementsByClass("D5aOJc").first()

            if(firstElement == secondElement && firstElement.text() != "") {

                println("[VoiceRecognition] Recognized stopped speaking, stopping specific recognition and handling input")
                microphoneButton.click() // Stop recognition
                SoundManager.playSound("end")

                val doc = Jsoup.parse(VoiceAssistant.driver.pageSource)
                val element = doc.getElementsByClass("D5aOJc").first()

                handleRecognition(element.text())
                success = true

            }
        }

    }

    private fun handleRecognition(textArea: String) {

        if(greetings.contains(textArea.toLowerCase())) {
            VoiceSynthesizer.speakText("Hallo, ${offers.random()}")
            startActiveRecognition()
        }else if(textArea.toLowerCase() == "stopp") {
            startRecognition()
        }

    }

}