package backend.utils

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

object TimetableFetcher {

    private fun getJson(): JSONObject {
        val file = File("files/files/timetable.json")
        val content = file.readText()
        return JSONParser().parse(content) as JSONObject
    }

    fun getToday(): JSONObject? {
        val day = SimpleDateFormat("EEEEE", Locale.ENGLISH).format(Date()).toLowerCase()

        return try {
            val json = getJson()[day] as JSONObject
            json["label"] =
                "${SimpleDateFormat("EEEEE").format(Date())} der ${SimpleDateFormat("dd. MMMMM").format(Date())}"

            json
        } catch (e: Exception) {
            null
        }
    }

    fun getTomorrow(): JSONObject? {
        val calender = Calendar.getInstance()
        calender.time = Date()
        calender.add(Calendar.DATE, 1)
        val date = calender.time
        val day = SimpleDateFormat("EEEEE", Locale.ENGLISH).format(date).toLowerCase()

        return try {
            val json = getJson()[day] as JSONObject
            json["label"] =
                "${SimpleDateFormat("EEEEE").format(date)} der ${SimpleDateFormat("dd. MMMMM").format(date)}"

            json
        } catch (e: Exception) {
            null
        }
    }

}