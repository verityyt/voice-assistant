package backend.commands.google

import backend.core.VoiceCommand
import backend.core.VoiceRecognition
import backend.core.VoiceSynthesizer
import backend.features.EmailClient
import org.jsoup.Jsoup
import java.util.ArrayList

class SearchCommand : VoiceCommand() {

    override val keywords: List<String> = listOf("google suche", "suche auf google", "starte google suche")

    private val whatQuestion =
        listOf("Nachwas soll ich suchen", "Was soll ich suchen", "Wonach soll ich suchen", "Was soll ich googlen")

    override fun perform(input: String) {
        /*VoiceSynthesizer.speakText(whatQuestion.random())

        val answer = VoiceRecognition.startReactiveRecognition()
        val url =
            "https://www.google.de/search?sxsrf=ALeKk02QNzZQzyJnR97HF-mgnsW_w46hgg%3A1605022285286&source=hp&ei=TbKqX--0Do6-Uoa1p-AJ&q=PLACEHOLDER&oq=PLACEHOLDER&gs_lcp=CgZwc3ktYWIQAzIECCMQJzIHCAAQFBCHAjICCAAyAggAMgIIADIFCAAQywEyAggAMgIIADICCAAyAggAOgUIABCxAzoICAAQsQMQgwE6BwgAELEDEEM6BAgAEEM6BQguELEDUMYDWJkXYIUYaABwAHgBgAGsAogB9QySAQc1LjMuMi4xmAEAoAEBqgEHZ3dzLXdperABAA&sclient=psy-ab&ved=0ahUKEwivj6z5pfjsAhUOnxQKHYbaCZwQ4dUDCAk&uact=5"

        Jarvis.driver.executeScript("window.open('','_blank');")
        Jarvis.driver.switchTo().window(ArrayList(Jarvis.driver.windowHandles)[2])
        Jarvis.driver.get(url.replace("PLACEHOLDER", answer.replace(" ", "+")))

        val parsed = Jsoup.parse(Jarvis.driver.pageSource)
        val entries = parsed.getElementsByClass("yuRUbf")
        val results = HashMap<String, String>()

        for (entry in entries) {
            val a = entry.select("a[href]").first()
            val span = a.getElementsByClass("DKV0Md").first().allElements.first()

            results[span.text()] = a.attr("href")
        }

        Jarvis.driver.close()
        VoiceSynthesizer.speakText("Ich habe ${results.size} Suchergebnisse gefunden. Soll ich sie ihnen per Email schicken?")

        checkAnswer(answer, results)
        /*VoiceRecognition.startRecognition()*/
    }

    private fun generateEmailText(results: HashMap<String, String>): String {
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

        return template.replace("PLACEHOLDER", replacement)*/
    }

    /*private fun checkAnswer(answer: String, results: HashMap<String, String>): String {
        val answer2 = VoiceRecognition.startReactiveRecognition()
        var result = ""

        when(answer2.toLowerCase()) {
            "ja" -> {
                Thread {
                    EmailClient.sendMail(
                        Jarvis.email,
                        "Suchergebnisse für '$answer'",
                        generateEmailText(results)
                    )
                }.start()
                VoiceSynthesizer.speakText("Die Email wurde erfolgreich gesendet. Überprüfen sie ihren Posteingang")
            }
            "nein" -> {
                VoiceSynthesizer.speakText("Okay")
            }
            else -> {
                VoiceSynthesizer.speakText("Ich konnte sie nicht verstehen, bitte wiederholen sie ihre Antwort!")
                result = checkAnswer(answer, results)
            }
        }

        return result
    }*/

}