package backend.utils

import org.jsoup.Jsoup

class Weather {

    fun getTemperature(): String {
        val html = Jsoup.parse(khttp.get(Jarvis.weatherUrl).text)
        return html.getElementsByClass("CurrentConditions--tempValue--3KcTQ").first().text().replace("°", "")
    }

    fun getWeather(): String {
        val html = Jsoup.parse(khttp.get(Jarvis.weatherUrl).text)
        return html.getElementsByClass("CurrentConditions--phraseValue--2xXSr").first().text()
    }

}