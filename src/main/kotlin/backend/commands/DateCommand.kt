package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceRecognition
import backend.core.VoiceSynthesizer
import java.text.SimpleDateFormat
import java.util.*

class DateCommand : VoiceCommand() {

    override val keywords: List<String> = listOf(
        "welcher tag ist heute",
        "welches datum haben wir",
        "welches datum ist es",
        "welches datum ist heute",
        "welches datum haben wir heute"
    )

    override fun perform(input: String) {

        val formatter = SimpleDateFormat("EEEEE.dd.MMMMM.yyyy")
        val date = formatter.format(Date())
        val parts = date.split(".")

        VoiceSynthesizer.speakText("Heute ist ${parts[0]} der ${parts[1]} ${parts[2]} ${parts[3]}")
        VoiceRecognition.startRecognition()
    }

}