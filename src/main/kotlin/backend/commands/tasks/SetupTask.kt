package backend.commands.tasks

import backend.core.*

class SetupTask : VoiceCommand() {

    override val needsReaction: Boolean = true

    override val keywords: List<String> = listOf()

    override var state: Int = 0

    override fun perform(input: String) {
        VoiceSynthesizer.speakText("Guten Tag sir, ich starte die Einrichtung. Soll ich neue Einstellungen importieren")
        SoundManager.playSound("start")
        state = 1
    }

    override fun reaction(input: String) {
        when (state) {
            1 -> {
                if (input == "ja") {
                    VoiceSynthesizer.speakText("Aus welcher Datei soll ich die Einstellungen importieren")
                    SoundManager.playSound("start")
                    state = 2
                } else if (input == "nein") {
                    VoiceSynthesizer.speakText("Alle Systeme startklar. Ich bin wieder online.")
                    VoiceRecognizer.currentCommand = null
                    VoiceRecognizer.activated = false
                    state = 0
                } else {
                    VoiceSynthesizer.speakText("Ich konnte sie leider nicht verstehen, bitte wiederholen sie ihre Antwort")
                    SoundManager.playSound("start")
                }
            }
            2 -> {
                VoiceSynthesizer.speakText("Importiere Einstellungen aus ${input}.json")
                val import = Configuration.import(input)

                if (import) {
                    VoiceSynthesizer.speakText("Import erfolgreich, Einstellungen wurden übernommen")
                    Configuration.setOptionsValue("setup", "false")

                    VoiceSynthesizer.speakText("Alle Systeme startklar. Ich bin wieder online.")
                    VoiceRecognizer.currentCommand = null
                    VoiceRecognizer.activated = false

                    state = 0
                } else {
                    VoiceSynthesizer.speakText("Das importieren aus ${input}.json ist fehlgeschlagen, bitte versuchen sie es mit einer anderen Datei")
                    SoundManager.playSound("start")
                }
            }
        }
    }

}