package backend.utils

import org.jsoup.Jsoup

object Weather {

    fun getTemperature(): String {
        val html = Jsoup.parse(khttp.get(VoiceAssistant.weatherUrl).text)
        return html.getElementsByClass("CurrentConditions--tempValue--3KcTQ").first().text().replace("°", "")
    }

    fun getWeather(): String {
        val html = Jsoup.parse(khttp.get(VoiceAssistant.weatherUrl).text)
        return html.getElementsByClass("CurrentConditions--phraseValue--2xXSr").first().text()
    }

}