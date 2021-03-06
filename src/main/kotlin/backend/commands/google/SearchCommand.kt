package backend.commands.google

import backend.core.SoundManager
import backend.core.VoiceCommand
import backend.core.VoiceRecognizer
import backend.core.VoiceSynthesizer
import backend.utils.EmailClient
import org.jsoup.Jsoup
import java.util.ArrayList

class SearchCommand : VoiceCommand() {

    override val needsReaction: Boolean = true

    override val keywords: List<String> =
        listOf("google suche", "suche auf google", "starte google suche", "google-suche", "starte google-suche")

    private val whatQuestion =
        listOf("Nachwas soll ich suchen", "Was soll ich suchen", "Wonach soll ich suchen", "Was soll ich googlen")

    override var state: Int = 0

    private var searchResults = HashMap<String, String>()
    private var searched = ""

    override fun perform(input: String) {
        VoiceSynthesizer.speakText(whatQuestion.random())
        VoiceRecognizer.activated = true
        SoundManager.playSound("start")

        state = 1
    }

    override fun reaction(input: String) {

        when (state) {
            1 -> {
                searched = input

                val url =
                    "https://www.google.de/search?sxsrf=ALeKk02QNzZQzyJnR97HF-mgnsW_w46hgg%3A1605022285286&source=hp&ei=TbKqX--0Do6-Uoa1p-AJ&q=PLACEHOLDER&oq=PLACEHOLDER&gs_lcp=CgZwc3ktYWIQAzIECCMQJzIHCAAQFBCHAjICCAAyAggAMgIIADIFCAAQywEyAggAMgIIADICCAAyAggAOgUIABCxAzoICAAQsQMQgwE6BwgAELEDEEM6BAgAEEM6BQguELEDUMYDWJkXYIUYaABwAHgBgAGsAogB9QySAQc1LjMuMi4xmAEAoAEBqgEHZ3dzLXdperABAA&sclient=psy-ab&ved=0ahUKEwivj6z5pfjsAhUOnxQKHYbaCZwQ4dUDCAk&uact=5"

                VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[0])
                VoiceAssistant.driver.executeScript("window.open('','_blank');")
                VoiceAssistant.driver.switchTo().window(ArrayList(VoiceAssistant.driver.windowHandles)[1])
                VoiceAssistant.driver.get(url.replace("PLACEHOLDER", input.replace(" ", "+")))

                val parsed = Jsoup.parse(VoiceAssistant.driver.pageSource)
                val entries = parsed.getElementsByClass("yuRUbf")

                for (entry in entries) {
                    val a = entry.select("a[href]").first()
                    val span = a.getElementsByClass("DKV0Md").first().allElements.first()

                    searchResults[span.text()] = a.attr("href")
                }

                VoiceAssistant.driver.close()
                VoiceSynthesizer.speakText("Ich habe ${searchResults.size} Suchergebnisse gefunden. Soll ich sie ihnen per Email schicken?")
                SoundManager.playSound("start")
                state = 2
            }

            2 -> {
                if (input == "ja") {
                    Thread {
                        EmailClient.sendMail(
                            VoiceAssistant.email,
                            "Suchergebnisse für '${searched.capitalize()}'",
                            generateEmailContent(searchResults)
                        )
                    }.start()
                    VoiceSynthesizer.speakText("Die Email wurde erfolgreich gesendet. Überprüfen sie ihren Posteingang")

                    VoiceRecognizer.currentCommand = null
                    VoiceRecognizer.activated = false

                    searchResults.clear()
                    searched = ""
                    state = 0
                } else if (input == "nein") {
                    VoiceSynthesizer.speakText("Okay")
                    VoiceRecognizer.currentCommand = null
                    VoiceRecognizer.activated = false

                    searchResults.clear()
                    searched = ""
                    state = 0
                } else {
                    VoiceSynthesizer.speakText("Bitte antworten sie mit ja oder nein!")
                    SoundManager.playSound("start")
                }
            }
        }

    }

    private fun generateEmailContent(results: HashMap<String, String>): String {
        val template = """<!DOCTYPE html>
<html lang=en">
<head>
    <meta charset="UTF-8">
</head>
<body>
<h3>Ich habe folgende Suchergebnisse gefunden:</h3>
PLACEHOLDER
</body>
</html>"""

        var replacement = ""

        for (result in results) {
            replacement += "- "
            replacement += """<a href="${result.value}">${result.key}</a><br><br>"""
        }

        return template.replace("PLACEHOLDER", replacement)
    }

}