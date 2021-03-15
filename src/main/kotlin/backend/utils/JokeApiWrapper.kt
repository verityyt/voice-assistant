package backend.utils

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

object JokeApiWrapper {
    
    private fun requestJoke(): String {
        val respond = khttp.get("https://v2.jokeapi.dev/joke/Any?lang=de")
        val json = JSONParser().parse(respond.text) as JSONObject

        if(json["error"] == true) {
            return json["error"].toString()
        }else {
            if(json["joke"] != null) {
                return json["joke"].toString()
            }else if(json["setup"] != null && json["delivery"] != null) {
                return "${json["setup"]}#5687#${json["delivery"]}"
            }
        }

        return "AGAIN"
    }

    fun getJoke(): String {
        val joke = requestJoke()

        if(joke == "AGAIN") {
            getJoke()
        }else {
            return joke
        }

        return ""
    }

}