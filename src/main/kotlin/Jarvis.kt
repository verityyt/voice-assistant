import backend.VoiceRecognition
import backend.VoiceSynthesizer
import backend.commands.*
import backend.commands.weather.TemperatureCommand
import backend.commands.weather.WeatherCommand
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.HashMap
import kotlin.system.exitProcess
import java.util.ArrayList


object Jarvis {

    @JvmStatic
    lateinit var driver: ChromeDriver

    private lateinit var tabs: ArrayList<String>

    val keyword = "hey jarvis"

    val commands = listOf(IntroduceCommand(), GreetingCommand(), ImBackCommand(), ByeCommand(), StopCommand(), ShutdownCommand(), NoCommand(), WeatherCommand(), TemperatureCommand())

    @JvmStatic
    fun main(args: Array<String>) {

        println("[VoiceAssistant] Starting WebDriver...")

        val prefs: MutableMap<String, Any> = HashMap()
        prefs["profile.default_content_setting_values.media_stream_mic"] = 1
        val options = ChromeOptions()
        options.setExperimentalOption("prefs", prefs)

        System.setProperty("webdriver.chrome.driver", "files/chromedriver.exe")
        driver = ChromeDriver(options)

        driver.get("https://translate.google.com/?hl=de&ui=tob&sl=de&tl=en&op=translate")

        driver.executeScript("window.open('','_blank');")

        tabs = ArrayList(driver.windowHandles)
        driver.switchTo().window(tabs[1])
        driver.get("https://ttsmp3.com/text-to-speech/German/")

        VoiceSynthesizer.speakText("Alle Systeme startklar. Ich bin wieder online.")

        VoiceRecognition.startRecognition()

    }

    fun shutdown() {
        driver.quit()
        exitProcess(-1)
    }

    fun switchTab(index: Int) {

        driver.switchTo().window(tabs[index])

    }

}