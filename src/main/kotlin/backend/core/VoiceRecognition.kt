package backend.core

import org.jsoup.Jsoup
import org.openqa.selenium.By

object VoiceRecognition {

    fun startRecognition() {

        Jarvis.switchTab(0)

        try {
            val clearButton = Jarvis.driver.findElement(By.className("GA2I6e"))
            clearButton.click()
        } catch (e: Exception) {
        }

        val microphoneButton = Jarvis.driver.findElement(By.className("MtoyUd"))

        println("[VoiceRecognition] Starting general recognition for keyword")
        microphoneButton.click() // Starting recognition

        var success = false

        while (!success) {
            if (Jarvis.driver.pageSource.toLowerCase().contains(Jarvis.keyword)) {

                if(Jarvis.locked) {
                    success = true
                    VoiceSynthesizer.speakText("Zugriff verweigert, bitte sagen sie mir ihren PIN Code")
                    val pin = startReactiveRecognition()

                    if(pin == Jarvis.pin) {
                        Jarvis.locked = false
                        VoiceSynthesizer.speakText("Korrekter PIN Code, entsperrt")
                        startRecognition()
                    }else {
                        VoiceSynthesizer.speakText("Zugriff verweigert, falscher PIN Code")
                        startRecognition()
                    }
                }

                println("[VoiceRecognition] Recognized keyword, stopping general recognition")
                microphoneButton.click() // Stopping recognition

                val clearButton = Jarvis.driver.findElement(By.className("GA2I6e"))
                clearButton.click() // Clearing textarea

                println("[VoiceRecognition] Starting specific recognition")

                if (listOf(true, false).random()) {
                    VoiceSynthesizer.speakText("Ja, sir?")
                    Jarvis.switchTab(0)
                }

                microphoneButton.click() // Restart recognition
                SoundManager.playSound("start")

                while (!success) {
                    val firstDoc = Jsoup.parse(Jarvis.driver.pageSource)
                    val firstElement = firstDoc.getElementsByClass("D5aOJc").first()

                    Thread.sleep(1000) // Checking if user stopped speaking within 1 seconds

                    val secondDoc = Jsoup.parse(Jarvis.driver.pageSource)
                    val secondElement = secondDoc.getElementsByClass("D5aOJc").first()

                    if (firstElement == secondElement && firstElement.text() != "") {

                        println("[VoiceRecognition] Recognized stopped speaking, stopping specific recognition and handling input")
                        microphoneButton.click() // Stop recognition
                        SoundManager.playSound("end")

                        val doc = Jsoup.parse(Jarvis.driver.pageSource)
                        val element = doc.getElementsByClass("D5aOJc").first()

                        handleRecognition(element.text())
                        success = true

                    }
                }
            }
        }

    }

    fun startActiveRecognition() {

        Jarvis.switchTab(0)
        try {
            val clearButton = Jarvis.driver.findElement(By.className("GA2I6e"))
            clearButton.click()
        } catch (e: Exception) {
        }

        val microphoneButton = Jarvis.driver.findElement(By.className("MtoyUd"))

        println("[VoiceRecognition] Starting new active specific recognition")
        microphoneButton.click() // Starting recognition
        SoundManager.playSound("start")

        var success = false

        while (!success) {
            val firstDoc = Jsoup.parse(Jarvis.driver.pageSource)
            val firstElement = firstDoc.getElementsByClass("D5aOJc").first()

            Thread.sleep(1000) // Checking if user stopped speaking within 1 seconds

            val secondDoc = Jsoup.parse(Jarvis.driver.pageSource)
            val secondElement = secondDoc.getElementsByClass("D5aOJc").first()

            if (firstElement == secondElement && firstElement.text() != "") {

                println("[VoiceRecognition] Recognized stopped speaking, stopping specific recognition and handling input")
                microphoneButton.click() // Stop recognition
                SoundManager.playSound("end")

                val doc = Jsoup.parse(Jarvis.driver.pageSource)
                val element = doc.getElementsByClass("D5aOJc").first()

                handleRecognition(element.text())
                success = true

            }
        }

    }

    fun startReactiveRecognition(): String {

        Jarvis.switchTab(0)
        try {
            val clearButton = Jarvis.driver.findElement(By.className("GA2I6e"))
            clearButton.click()
        } catch (e: Exception) {
        }

        val microphoneButton = Jarvis.driver.findElement(By.className("MtoyUd"))

        println("[VoiceRecognition] Starting new reactive specific recognition")
        microphoneButton.click() // Starting recognition
        SoundManager.playSound("start")

        while (true) {
            val firstDoc = Jsoup.parse(Jarvis.driver.pageSource)
            val firstElement = firstDoc.getElementsByClass("D5aOJc").first()

            Thread.sleep(1000) // Checking if user stopped speaking within 1 seconds

            val secondDoc = Jsoup.parse(Jarvis.driver.pageSource)
            val secondElement = secondDoc.getElementsByClass("D5aOJc").first()

            if (firstElement == secondElement && firstElement.text() != "") {

                println("[VoiceRecognition] Recognized stopped speaking, stopping reactive recognition and returning input")
                microphoneButton.click() // Stop recognition
                SoundManager.playSound("end")

                val doc = Jsoup.parse(Jarvis.driver.pageSource)
                val element = doc.getElementsByClass("D5aOJc").first()

                return element.text()

            }
        }
    }

    private fun handleRecognition(textArea: String) {

        var found = false

        for (command in Jarvis.commands) {
            if (command.keywords.contains(textArea.toLowerCase())) {
                command.perform(textArea.toLowerCase())
                found = true
            }
        }

        if (!found) {
            VoiceSynthesizer.speakText("Es tut mir leid aber ich weiß nicht was sie mit '$textArea' meinen")
            startRecognition()
        }

    }

}