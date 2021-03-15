package backend.commands

import backend.core.SoundManager
import backend.core.VoiceCommand
import backend.core.VoiceRecognizer
import backend.core.VoiceSynthesizer

class GreetingCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("hallo", "guten morgen", "guten tag", "guten abend", "servus", "Hi")

    val offers = listOf("wie kann ich ihnen helfen?", "kann ich irgendetwas für sie tun?", "kann ich ihnen irgendwie behilflich sein?")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Hallo, ${offers.random()}")
        SoundManager.playSound("start")
        VoiceRecognizer.activated = true
    }

}