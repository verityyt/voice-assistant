package backend.utils

import org.jsoup.Jsoup

object WeatherFetcher {

    fun getTemperature(): String {
        val html = Jsoup.parse(khttp.get(VoiceAssistant.weatherUrl).text)
        return html.getElementsByClass("CurrentConditions--tempValue--3a50n").first().text().replace("°", "")
    }

    fun getWeather(): String {
        val html = Jsoup.parse(khttp.get(VoiceAssistant.weatherUrl).text)
        return html.getElementsByClass("CurrentConditions--phraseValue--2Z18W").first().text()
    }

}