package backend.commands

import backend.VoiceCommand
import backend.VoiceRecognition
import backend.VoiceSynthesizer

class NoCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("nein", "nichts")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Ok sir, ich werde mich bereit halten.")
        VoiceRecognition.startRecognition()
    }

}