package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import java.text.SimpleDateFormat
import java.util.*

class TimeCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("wie viel uhr ist es", "wie spät ist es", "wie früh ist es")

    override var state: Int = 0

    override fun perform(input: String) {
        val formatter = SimpleDateFormat("HH:mm")
        val time = formatter.format(Date())
        val parts = time.split(":")

        var hours = parts[0]
        var minutes = parts[1]

        if(minutes.startsWith("0")) {
            minutes = minutes.replaceFirst("0","")
        }

        if(hours.startsWith("0")) {
            hours = hours.replaceFirst("0","")
        }

        VoiceSynthesizer.speakText("Es ist $hours uhr $minutes")
    }

}