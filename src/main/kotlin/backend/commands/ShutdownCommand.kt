package backend.commands

import backend.VoiceCommand
import backend.VoiceSynthesizer

class ShutdownCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("shutdown")

    val byes = listOf("Bis bald", "Wir hören uns")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("${byes.random()}. [shutdown]")
    }

}