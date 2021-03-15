package backend.core

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File

object Configuration {

    var file = File("files/default.json")
    var options = File("files/options.json")

    private fun check(): Boolean = file.exists()
    private fun checkOptions(): Boolean = options.exists()

    fun create() {
        if (!check()) {
            println("[Configuration] Creating configuration...")

            val json = JSONObject()

            json["name"] = "J.A.R.V.I.S."
            json["keyword"] = "hey jarvis"
            json["weather.com"] =
                "https://weather.com/de-DE/wetter/heute/l/5ca23443513a0fdc1d37ae2ffaf5586162c6fe592a66acc9320a0d0536be1bb9"
            json["email"] = "jarvis@verity-network.de"
            json["pin"] = "0000"
            json["voice"] = "male"

            file.writeText(json.toJSONString())

            println("[Configuration] Configuration created!")
        }

        if (!checkOptions()) {
            println("[Configuration] Creating options configuration...")

            val json = JSONObject()

            json["filename"] = "default"
            json["setup"] = "true"
            options.writeText(json.toJSONString())

            println("[Configuration] Options Configuration created!")
        }

        val optionsJson = JSONParser().parse(options.readText()) as JSONObject
        import(optionsJson["filename"].toString())

    }

    fun get(key: String): String {
        val json = JSONParser().parse(file.readText()) as JSONObject

        return json[key].toString()
    }

    fun getFromOptions(key: String): String {
        val json = JSONParser().parse(options.readText()) as JSONObject

        return json[key].toString()
    }

    fun setOptionsValue(key: String, value: String) {
        val json = JSONParser().parse(options.readText()) as JSONObject
        json[key] = value
        options.writeText(json.toJSONString())
    }

    fun import(name: String): Boolean {
        file = File("files/$name.json")

        if(file.exists()) {
            val json = JSONParser().parse(file.readText()) as JSONObject

            VoiceAssistant.name = json["name"].toString()
            VoiceAssistant.keyword = json["keyword"].toString()
            VoiceAssistant.weatherUrl = json["weather.com"].toString().replace("\\","")
            VoiceAssistant.email = json["email"].toString()
            VoiceAssistant.pin = json["pin"].toString()
            VoiceAssistant.voice = json["voice"].toString()

            setOptionsValue("filename", name)

            return true
        }

        return false
    }

}