package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class RepeatCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf(
        "kannst du das bitte wiederholen",
        "wiederholen",
        "wiederholen bitte",
        "kannst du das nochmal sagen",
        "bitte wiederhole das"
    )

    override fun perform(input: String) {
        VoiceSynthesizer.speakLatest()
    }

    override var state: Int = 0

}