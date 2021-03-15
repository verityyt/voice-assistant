package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class NoCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("nein", "nichts")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Ok sir, ich werde mich bereit halten.")
    }

}