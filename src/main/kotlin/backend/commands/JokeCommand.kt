package backend.commands

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.JokeApiWrapper

class JokeCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("erzähl mir einen witz","kannst du bitte einen witz erzählen", "kannst du einen witz erzählen", "sag was lustiges")

    override fun perform(input: String) {
        val joke = JokeApiWrapper.getJoke()

        if(joke.contains("#5687#")) {
            val parts = joke.split("#5687#")

            for(part in parts) {
                VoiceSynthesizer.speakText(part)
                Thread.sleep(1500)
            }

        }else {
            VoiceSynthesizer.speakText(joke)
        }
    }

    override var state: Int = 0

}