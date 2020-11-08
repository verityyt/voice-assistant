package backend.commands

import backend.VoiceCommand
import backend.VoiceRecognition
import backend.VoiceSynthesizer

class ImBackCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("ich bin wieder da", "ich bin wieder zurück", "ich bin wieder zuhause")

    val offers = listOf("wie kann ich ihnen helfen?", "kann ich irgendetwas für sie tun?", "kann ich ihnen irgendwie behilflich sein?")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Willkommen zuhause sir. ${offers.random()}")
        VoiceRecognition.startActiveRecognition()
    }

}