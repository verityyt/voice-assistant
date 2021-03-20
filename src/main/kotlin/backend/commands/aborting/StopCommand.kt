package backend.commands.aborting

import backend.core.VoiceCommand
import hud.HUD

class StopCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("stopp", "abbruch", "abbrechen")

    override var state: Int = 0

    override fun perform(input: String) {
        HUD.hide()
    }

}