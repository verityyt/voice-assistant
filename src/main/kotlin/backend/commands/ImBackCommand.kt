package backend.commands

import backend.core.SoundManager
import backend.core.VoiceCommand
import backend.core.VoiceRecognizer
import backend.core.VoiceSynthesizer

class ImBackCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("ich bin wieder da", "ich bin wieder zurück", "ich bin zurück", "ich bin wieder zuhause")

    val offers = listOf("wie kann ich ihnen helfen?", "kann ich irgendetwas für sie tun?", "kann ich ihnen irgendwie behilflich sein?")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Willkommen zuhause sir. ${offers.random()}")
        VoiceRecognizer.activated = true
        SoundManager.playSound("start")
    }

}