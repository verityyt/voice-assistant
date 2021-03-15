package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class ByeCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("tschüss", "bis später", "bis bald")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("$input. Ich warte solange auf sie")
    }

}