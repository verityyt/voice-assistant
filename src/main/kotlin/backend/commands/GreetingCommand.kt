package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceRecognition
import backend.core.VoiceSynthesizer

class GreetingCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("hallo", "guten morgen", "guten tag", "guten abend", "servus", "Hi")

    val offers = listOf("wie kann ich ihnen helfen?", "kann ich irgendetwas für sie tun?", "kann ich ihnen irgendwie behilflich sein?")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Hallo, ${offers.random()}")
        VoiceRecognition.startActiveRecognition()
    }

}