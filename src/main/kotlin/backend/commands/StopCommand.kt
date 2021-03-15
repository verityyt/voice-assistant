package backend.commands

import backend.core.VoiceCommand

class StopCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("stopp", "abbruch", "abbrechen")

    override fun perform(input: String) { }

}