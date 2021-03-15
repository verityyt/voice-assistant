package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceRecognition

class StopCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("stopp", "abbruch", "abbrechen")

    override fun perform(input: String) {
        /*VoiceRecognition.startRecognition()*/
    }


}