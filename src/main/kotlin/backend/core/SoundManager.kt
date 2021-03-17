package backend.core

import hud.HUD
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream

object SoundManager {

    fun playSound(name: String) {
        val clip = AudioSystem.getClip()
        val inputStream: AudioInputStream = AudioSystem.getAudioInputStream(File("files/sounds/$name.wav"))
        clip.open(inputStream)
        clip.start()

        if(name == "start") {
            HUD.play("waiting")
            HUD.show()
        }
    }

}