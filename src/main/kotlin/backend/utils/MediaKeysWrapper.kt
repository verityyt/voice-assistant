package backend.utils

object MediaKeysWrapper {

    fun pause() {
        execute("pause")
    }

    fun play() {
        execute("play")
    }

    fun next() {
        execute("next")
    }

    fun previous() {
        execute("pre")
    }

    private fun execute(arg: String) {
        Runtime.getRuntime().exec("python files\\media_keys.py $arg")
    }

}