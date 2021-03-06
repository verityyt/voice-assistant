package backend.commands.weather

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.WeatherFetcher

class WeatherCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("wie ist das wetter" , "wie ist das wetter draußen", "wie ist das wetter gerade")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Draußen sind es gerade ${WeatherFetcher.getTemperature()} grad celsius und das Wetter ist ${WeatherFetcher.getWeather()}.")
    }


}