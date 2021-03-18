package backend.commands.weather

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.Weather

class TemperatureCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf("wie warm ist es draußen" , "wie kalt ist es draußen", "wie warm ist es", "wie kalt ist es", "wie warm ist es gerade", "wie kalt ist es gerade")

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Draußen sind es gerade ${Weather.getTemperature()} grad celsius.")
    }


}