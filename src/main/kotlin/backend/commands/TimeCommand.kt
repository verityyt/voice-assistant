package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import java.text.SimpleDateFormat
import java.util.*

class TimeCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("wie viel uhr ist es", "wie spät ist es", "wie früh ist es")

    override fun perform(input: String) {
        val formatter = SimpleDateFormat("HH:mm")
        val time = formatter.format(Date())
        val parts = time.split(":")

        VoiceSynthesizer.speakText("Es ist ${parts[0]} uhr ${parts[1]}")
    }

}