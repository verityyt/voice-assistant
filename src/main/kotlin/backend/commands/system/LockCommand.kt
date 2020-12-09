package backend.commands.system

import backend.core.VoiceCommand
import backend.core.VoiceRecognition
import backend.core.VoiceSynthesizer

class LockCommand : VoiceCommand() {
    override val keywords: List<String> = listOf("sperren", "sperre dich", "versperren")

    override fun perform(input: String) {

        VoiceSynthesizer.speakText("Zugriff verweigert, bitte sagen sie mir ihren PIN Code")
        val answer = VoiceRecognition.startReactiveRecognition()

        if(answer == Jarvis.pin) {
            VoiceSynthesizer.speakText("Korrekter PIN Code, gesperrt")
            Jarvis.locked = true
            VoiceRecognition.startRecognition()
        }else {
            VoiceSynthesizer.speakText("Zugriff verweigert, falscher PIN Code")
            VoiceRecognition.startRecognition()
        }

    }

}