package backend.commands.corona

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.IncidenceFetcher

class IncidenceCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf(
        "was ist der aktuelle inzidenz wert",
        "was ist der inzidenz wert",
        "wie ist der aktuelle inzidenz wert",
        "wie ist der inzidenz wert",
        "wie hoch ist der inzidenz wert",
        "wie hoch ist der inzidenz wert gerade",
        "wie hoch ist der inzidenz wert aktuell",
        "wie niedrig ist der inzidenz wert",
        "wie niedrig ist der inzidenz wert gerade",
        "wie niedrig ist der inzidenz wert aktuell"
    )

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Der aktuelle inzidenz wert liegt bei ${IncidenceFetcher.getIncidence().toString().replace(".", ",")} pro 100000 einwohnern in den letzten 7 tagen")
    }

    override var state: Int = 0

}