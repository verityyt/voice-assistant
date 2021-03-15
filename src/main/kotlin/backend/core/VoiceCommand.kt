package backend.core

abstract class VoiceCommand {

    abstract val needsReaction: Boolean

    abstract val keywords: List<String>

    abstract fun perform(input: String)

    open fun reaction(input: String) {}

}