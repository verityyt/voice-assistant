package backend.commands.weather

import backend.core.VoiceCommand
import backend.core.VoiceRecognition
import backend.core.VoiceSynthesizer
import backend.features.Weather

class WeatherCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("wie ist das wetter" , "wie ist das wetter draußen", "wie ist das wetter gerade")

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Draußen sind es gerade ${Weather().getTemperature()} grad celsius und das Wetter ist ${Weather().getWeather()}.")
        /*VoiceRecognition.startRecognition()*/
    }


}