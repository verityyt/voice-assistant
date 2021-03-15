package backend.commands.system

import backend.core.SoundManager
import backend.core.VoiceCommand
import backend.core.VoiceRecognizer
import backend.core.VoiceSynthesizer

class LockCommand : VoiceCommand() {

    override val needsReaction: Boolean = true

    override val keywords: List<String> = listOf("sperren", "sperre dich", "versperren")

    private var state = 0

    override fun perform(input: String) {

        VoiceSynthesizer.speakText("Zugriff verweigert, bitte sagen sie mir ihren PIN Code")
        SoundManager.playSound("start")
        VoiceRecognizer.activated = true
        state = 1

    }

    override fun reaction(input: String) {
        when(state) {
            1 -> {
                if(input == VoiceAssistant.pin) {
                    VoiceSynthesizer.speakText("Korrekter PIN Code, gesperrt")
                    VoiceAssistant.locked = true
                    VoiceRecognizer.currentCommand = null
                    VoiceRecognizer.activated = false
                }else {
                    VoiceSynthesizer.speakText("Zugriff verweigert, falscher PIN Code. Bitte wiederholen sie ihren pin code.")
                    SoundManager.playSound("start")
                }
            }
        }
    }

}