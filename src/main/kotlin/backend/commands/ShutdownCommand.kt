package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class ShutdownCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("shutdown")

    val byes = listOf("Bis bald", "Wir hören uns")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("${byes.random()}. [shutdown]")
    }

}