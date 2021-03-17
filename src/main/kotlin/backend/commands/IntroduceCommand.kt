package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer

class IntroduceCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> =
        listOf("stell dich vor", "stell dich bitte vor", "kannst du dich bitte vorstellen")

    override var state: Int = 0

    private val sentences =
        listOf(
            "Gestatten Sie mir, mich vorzustellen. Ich bin %NAME% eine virtuell künstliche Intelligenz und ich bin hier, um Ihnen so gut ich kann zu helfen. 24 Stunden am Tag, 7 Tage die Woche."
        )

    override fun perform(input: String) {
        val formattedName = VoiceAssistant.name.replace(".", "").toLowerCase().capitalize()
        val sentence = sentences.random()
        VoiceSynthesizer.speakText(
            sentence.replace("%NAME%", formattedName).replace("%ALIAS%", VoiceAssistant.alias)
                .replace("%USER%", VoiceAssistant.username)
        )
    }

}