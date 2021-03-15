package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class NoCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("nein", "nichts")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Ok sir, ich werde mich bereit halten.")
    }

}