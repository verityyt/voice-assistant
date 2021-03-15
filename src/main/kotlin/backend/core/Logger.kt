package backend.core

object Logger {

    private val debug = true

    fun info(text: String, name: String) {
        println("[INFO] ${name.split(".").last()}: $text")
    }

    fun debug(text: String, name: String) {
        if(debug) {
            println("[DEBUG] ${name.split(".").last()}: $text")
        }
    }

}