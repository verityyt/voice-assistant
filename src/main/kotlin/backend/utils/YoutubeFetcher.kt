package backend.utils

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.jsoup.Jsoup
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.awt.Desktop
import java.net.URI
import java.util.ArrayList

object YoutubeFetcher {

    fun getTop(query: String): String {
        val header = mapOf("Accept" to "application/json")
        val url =
            "https://youtube.googleapis.com/youtube/v3/search?part=snippet&channelType=any&eventType=none&maxResults=25&order=searchSortUnspecified&q=$query&key=${VoiceAssistant.youtubeApiKey}"
        val req = khttp.get(url, header)

        val json = JSONParser().parse(req.text) as JSONObject
        val items = json["items"] as JSONArray

        val first = items[0] as JSONObject
        val firstId = first["id"] as JSONObject
        val firstVideoID = firstId["videoId"]

        return "https://www.youtube.com/watch?v=$firstVideoID"
    }

}