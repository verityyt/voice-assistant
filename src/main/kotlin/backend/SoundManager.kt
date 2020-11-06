package backend

import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream

object SoundManager {

    fun playSound(name: String) {
        val clip = AudioSystem.getClip()
        val inputStream: AudioInputStream = AudioSystem.getAudioInputStream(File("files/$name.wav"))
        clip.open(inputStream)
        clip.start()
    }

}