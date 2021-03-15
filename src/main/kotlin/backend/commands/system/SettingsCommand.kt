package backend.commands.system

import backend.core.*

class SettingsCommand : VoiceCommand() {

    override val needsReaction: Boolean = true

    override val keywords: List<String> = listOf(
        "importiere andere einstellungen",
        "übernehme andere einstellungen",
        "übernehme neue einstellungen",
        "importiere neue einstellungen"
    )

    private var state = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Aus welcher Datei soll ich die Einstellungen übernehmen?")
        SoundManager.playSound("start")
        VoiceRecognizer.activated = true
        state = 1
    }

    override fun reaction(input: String) {
        SoundManager.playSound("end")

        if (state == 1) {
            VoiceSynthesizer.speakText("Importiere Einstellungen aus ${input}.json")

            val import = Configuration.import(input)

            if (import) {
                VoiceSynthesizer.speakText("Import erfolgreich, Einstellungen wurden übernommen.")
                VoiceRecognizer.currentCommand = null
                VoiceRecognizer.activated = false
                state = 0
            } else {
                VoiceSynthesizer.speakText("Das importieren aus ${input}.json ist fehlgeschlagen, bitte versuchen sie es mit einer anderen Datei.")
                SoundManager.playSound("start")
            }
        }

    }

}