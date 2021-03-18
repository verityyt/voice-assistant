package backend.commands.music

import backend.core.SoundManager
import backend.core.VoiceCommand
import backend.core.VoiceRecognizer
import backend.core.VoiceSynthesizer
import backend.utils.YoutubeFetcher
import java.awt.Desktop
import java.net.URI

class MusicCommand : VoiceCommand() {

    override val needsReaction: Boolean = true

    override val keywords: List<String> = listOf("spiele musik", "spiele musik von youtube")

    private val answers = listOf("Was soll ich spielen", "Welchen song willst du hören", "Was willst du hören")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText(answers.random())
        VoiceRecognizer.activated = true
        SoundManager.playSound("start")

        state = 1
    }

    override var state: Int = 0

    override fun reaction(input: String) {
        when(state) {
            1 -> {
                Desktop.getDesktop().browse(URI(YoutubeFetcher.getTop(input)))
            }
        }
    }

}