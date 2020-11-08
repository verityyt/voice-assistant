package backend.commands

import backend.VoiceCommand
import backend.VoiceRecognition

class StopCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("stopp", "abbruch", "abbrechen")

    override fun perform(input: String) {
        VoiceRecognition.startRecognition()
    }


}