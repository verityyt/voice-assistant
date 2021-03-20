package backend.commands.media

import backend.core.VoiceCommand
import backend.utils.MediaKeysWrapper

class MediaPauseCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("pause", "musik pause", "music pause", "video pause", "drück pause")

    override fun perform(input: String) {
        MediaKeysWrapper.pause()
    }

    override var state: Int = 0

}