package backend.commands

import backend.VoiceCommand
import backend.VoiceRecognition
import backend.VoiceSynthesizer
import java.text.SimpleDateFormat
import java.util.*

class TimeCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("wie viel uhr ist es", "wie spät ist es", "wie früh ist es")

    override fun perform(input: String) {
        val formatter = SimpleDateFormat("HH:mm")
        val time = formatter.format(Date())
        val parts = time.split(":")

        VoiceSynthesizer.speakText("Es ist ${parts[0]} uhr ${parts[1]}")
        VoiceRecognition.startRecognition()
    }

}