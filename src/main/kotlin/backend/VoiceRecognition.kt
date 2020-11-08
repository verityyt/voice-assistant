package backend

import org.jsoup.Jsoup
import org.openqa.selenium.By

object VoiceRecognition {

    val offers = listOf("Wie kann ich ihnen helfen?", "Kann ich irgendetwas für sie tun?", "Kann ich ihnen irgendwie behilflich sein?")

    fun startRecognition() {

        VoiceAssistant.switchTab(0)

        try {
            val clearButton = VoiceAssistant.driver.findElement(By.className("GA2I6e"))
            clearButton.click()
        }catch (e: Exception) { }

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
                VoiceSynthesizer.speakText("Ja, sir?")
                VoiceAssistant.switchTab(0)
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

    fun startActiveRecognition() {

        VoiceAssistant.switchTab(0)
        try {
            val clearButton = VoiceAssistant.driver.findElement(By.className("GA2I6e"))
            clearButton.click()
        }catch (e: Exception) { }

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

        for(command in VoiceAssistant.commands) {
            if(command.keywords.contains(textArea.toLowerCase())) {
                command.perform(textArea.toLowerCase())
            }
        }

        when(textArea.toLowerCase()) {
            "ich bin wieder da" -> {
                VoiceSynthesizer.speakText("Willkommen zuhause sir. ${offers.random()}")
                startActiveRecognition()
            }
            "tschüss" -> {
                VoiceSynthesizer.speakText("Bis später. Ich warte solange auf sie")
                startRecognition()
            }
            "stopp" -> {
                startRecognition()
            }
            "shutdown" -> {
                VoiceSynthesizer.speakText("Bis bald. [stop]")
            }
        }

    }

}