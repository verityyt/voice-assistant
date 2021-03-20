package backend.commands.media

import backend.core.VoiceCommand
import backend.utils.MediaKeysWrapper

class MediaPreviousCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("vorheriger", "vorheriger song", "vorheriges video","zurück")

    override fun perform(input: String) {
        MediaKeysWrapper.previous()
    }

    override var state: Int = 0

}