package backend.features

import org.jsoup.Jsoup
import secrets.weatherUrl

class Weather {

    fun getTemperature(): String {
        val html = Jsoup.parse(khttp.get(weatherUrl).text)
        val temp = html.getElementsByClass("CurrentConditions--tempValue--3KcTQ").first().text().replace("°","")
        return temp
    }

    fun getWeather(): String {
        val html = Jsoup.parse(khttp.get(weatherUrl).text)
        val weather = html.getElementsByClass("CurrentConditions--phraseValue--2xXSr").first().text()
        return weather
    }

}