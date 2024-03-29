package backend.commands.calender

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import java.text.SimpleDateFormat
import java.util.*

class DateCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf(
        "welchen tag haben wir heute",
        "welcher tag ist heute",
        "welches datum haben wir",
        "welches datum ist es",
        "welches datum ist heute",
        "welches datum haben wir heute"
    )

    override var state: Int = 0

    override fun perform(input: String) {
        val formatter = SimpleDateFormat("EEEEE.dd.MMMMM.yyyy")
        val date = formatter.format(Date())
        val parts = date.split(".")

        VoiceSynthesizer.speakText("Heute ist ${parts[0]} der ${parts[1]} ${parts[2]} ${parts[3]}")
    }

}