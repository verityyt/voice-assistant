package backend.commands.media

import backend.core.VoiceCommand
import backend.utils.MediaKeysWrapper

class MediaPlayCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> =
        listOf("play", "musik play", "music play", "video play", "drück play", "weiter", "drück weiter")

    override fun perform(input: String) {
        MediaKeysWrapper.play()
    }

    override var state: Int = 0

}