package backend

abstract class VoiceCommand {

    abstract val keywords: List<String>

    abstract fun perform(input: String)

}