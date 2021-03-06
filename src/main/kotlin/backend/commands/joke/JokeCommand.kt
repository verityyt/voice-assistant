package backend.commands.joke

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.JokeApiFetcher

class JokeCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("erzähl mir einen witz","kannst du bitte einen witz erzählen", "kannst du einen witz erzählen", "sag was lustiges")

    override fun perform(input: String) {
        val joke = JokeApiFetcher.getJoke()

        if(joke.contains("#5687#")) {
            val parts = joke.split("#5687#")

            for(part in parts) {
                VoiceSynthesizer.speakText(part)
                Thread.sleep(1000)
            }

        }else {
            VoiceSynthesizer.speakText(joke)
        }
    }

    override var state: Int = 0

}