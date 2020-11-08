package backend.commands

import backend.VoiceCommand
import backend.VoiceRecognition
import backend.VoiceSynthesizer

class VorstellenCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("stell dich vor", "stell dich bitte vor", "kannst du dich bitte vorstellen")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Hallo, ich bin Jarvis der neue virtuelle Assistent.")
        VoiceRecognition.startRecognition()
    }

}