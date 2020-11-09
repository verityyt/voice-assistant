package backend.commands.system

import backend.Configuration
import backend.VoiceCommand
import backend.VoiceRecognition
import backend.VoiceSynthesizer

class SettingsCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("importiere andere einstellungen", "übernehme andere einstellungs")

    override fun perform(input: String) {

        VoiceSynthesizer.speakText("Aus welcher Datei soll ich die Einstellungen übernehmen?")
        val answer = VoiceRecognition.startReactiveRecognition()

        if(answer != "") {

            VoiceSynthesizer.speakText("Importiere neue Einstellungen...")
            val import = Configuration.import(answer.toLowerCase())

            if(import) {
                VoiceSynthesizer.speakText("Import erfolgreich, neue Einstellungen wurden übernommen.")
            }else {
                VoiceSynthesizer.speakText("Das importieren dieser Datei ist fehlgeschlagen, bitte versuchen sie es mit einer anderen Datei.")
            }

        }else {
            VoiceSynthesizer.speakText("Entschludigen sie aber ich konnte sie nicht verstehen. Bitte wiederholen sie Befehl.")
        }

        VoiceRecognition.startRecognition()
    }

}