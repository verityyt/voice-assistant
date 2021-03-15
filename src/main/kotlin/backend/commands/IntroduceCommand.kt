package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class IntroduceCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("stell dich vor", "stell dich bitte vor", "kannst du dich bitte vorstellen")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Hallo, ich bin Jarvis der neue virtuelle Assistent.")
    }

}