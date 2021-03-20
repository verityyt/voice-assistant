package backend.commands.media

import backend.core.VoiceCommand
import backend.utils.MediaKeysWrapper

class MediaNextCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("nächster", "nächster song", "nächstes video")

    override fun perform(input: String) {
        MediaKeysWrapper.next()
    }

    override var state: Int = 0

}