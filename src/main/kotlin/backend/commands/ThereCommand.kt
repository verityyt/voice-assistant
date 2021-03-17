package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class ThereCommand : VoiceCommand() {

    override val needsReaction: Boolean = false
    override val keywords: List<String> = listOf("bist du da", "bist du hier")

    private val answers = listOf(
        "natürlich",
        "natürlich, sir",
        "ja",
        "ja, sir",
        "ich bin immer für sie da",
        "ich bin immer für sie da, sir"
    )

    override fun perform(input: String) {
        VoiceSynthesizer.speakText(answers.random())
    }

    override var state: Int = 0

}