package backend.core

import hud.HUD
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream

object SoundManager {

    fun playSound(name: String) {
        if (name != "start") {
            val clip = AudioSystem.getClip()
            val inputStream: AudioInputStream = AudioSystem.getAudioInputStream(File("files/sounds/$name.wav"))
            clip.open(inputStream)
            clip.start()
        } else {
            HUD.play("waiting")
            HUD.show()
        }

        /*
        *
        * If you're confused:
        * This whole thing with those weird if-statements,
        * is supposed to play not start sound and to show and
        * play the waiting on start-sounds actually position/time
        *
        */

    }

}