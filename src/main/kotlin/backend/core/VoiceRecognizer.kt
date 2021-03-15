package backend.core

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import kotlin.math.truncate
import kotlin.random.Random
import kotlin.system.exitProcess

object VoiceRecognizer {

    private lateinit var pythonThread: Process
    private lateinit var socket: Socket
    private var currentInput = ""

    var currentCommand: VoiceCommand? = null
    var activated = false

    private var unlockState = 0

    fun startup() {
        startPython()
        startRecognition()
        startListening()
    }

    fun shutdown() {
        pythonThread.destroy()
    }

    private fun startPython() {
        pythonThread = Runtime.getRuntime().exec("python files\\speech_recognizer.py")
    }

    private fun startRecognition() {
        socket = ServerSocket(9090).accept()

        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

        Thread {
            while (true) {
                var msg = reader.readLine()

                when (msg) {
                    "LISTENING_NOW" -> {
                        currentInput = "Started listening!"
                    }
                    "CLIENT_DISCONNECTED" -> {
                        exitProcess(-1)
                    }
                    "COULD_NOT_UNDERSTAND" -> {
                        currentInput = "No input!"
                    }
                    else -> {
                        if (msg.startsWith("RECOGNIZED:")) {
                            msg = msg.replace("RECOGNIZED:", "")
                            currentInput = msg
                        } else {
                            println("'$msg'")
                        }
                    }
                }
            }
        }.start()
    }

    private fun startListening() {
        Thread {
            var last = ""

            while (true) {
                val cur = currentInput

                if (last != cur) {
                    Logger.debug("New input from backend => $cur", this.javaClass.name)
                    last = cur

                    if (cur != "Started listening!" && cur != "No input!") {
                        handleRecognition(cur.toLowerCase())
                    }
                } else {
                    print("")
                }
            }
        }.start()
    }

    private fun handleRecognition(text: String) {

        if (!activated) {
            if (text.contains(VoiceAssistant.keyword.toLowerCase())) {
                if (VoiceAssistant.locked) {
                    if (unlockState == 0) {
                        VoiceSynthesizer.speakText("Zugriff verweigert, bitte sagen sie ihren PIN Code")
                        SoundManager.playSound("start")
                        activated = true
                        unlockState = 1
                    }
                } else {
                    Logger.info("Detected keyword => Listening now", this.javaClass.name)

                    if (Random.nextBoolean()) {
                        VoiceSynthesizer.speakText("Ja, sir?")
                    }


                    SoundManager.playSound("start")
                    activated = true
                }
            } else if (VoiceAssistant.keyword.toLowerCase().contains("jarvis") && (text.contains("davis") || text.contains("tarvis"))) {
                if (VoiceAssistant.locked) {
                    if (unlockState == 0) {
                        VoiceSynthesizer.speakText("Zugriff verweigert, bitte sagen sie ihren PIN Code")
                        SoundManager.playSound("start")
                        activated = true
                        unlockState = 1
                    }
                } else {
                    Logger.info("Detected keyword => Listening now", this.javaClass.name)

                    if (Random.nextBoolean()) {
                        VoiceSynthesizer.speakText("Ja, sir?")
                    }


                    SoundManager.playSound("start")
                    activated = true
                }
            }
        } else {
            SoundManager.playSound("end")

            if (VoiceAssistant.locked && unlockState == 1) {
                if (text == VoiceAssistant.pin) {
                    VoiceSynthesizer.speakText("Korrekter PIN Code, entsperrt")
                    VoiceAssistant.locked = false
                    activated = false
                    unlockState = 0
                } else {
                    VoiceSynthesizer.speakText("Zugriff verweigert, falscher PIN Code. Bitte wiederholen sie ihren pin code.")
                    SoundManager.playSound("start")
                }

            } else {
                if (currentCommand != null && currentCommand!!.needsReaction) {
                    currentCommand!!.reaction(text.toLowerCase())
                } else {
                    activated = false

                    var found = false

                    for (command in VoiceAssistant.commands) {
                        if (command.keywords.contains(text.toLowerCase())) {
                            println("Called ${command.javaClass.name}")

                            command.perform(text.toLowerCase())
                            currentCommand = command
                            found = true
                        }
                    }

                    if (!found) {
                        VoiceSynthesizer.speakText("Es tut mir leid aber ich weiß nicht was sie mit $text meinen")
                    }
                }
            }
        }

    }

}