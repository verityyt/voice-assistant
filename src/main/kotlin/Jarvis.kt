import backend.Configuration
import backend.VoiceRecognition
import backend.VoiceSynthesizer
import backend.commands.*
import backend.commands.google.SearchCommand
import backend.commands.system.SettingsCommand
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

    var keyword = ""
    var weatherUrl = ""
    var email = ""
    var name = ""

    val commands = listOf(
        IntroduceCommand(),
        GreetingCommand(),
        ImBackCommand(),
        ByeCommand(),
        StopCommand(),
        ShutdownCommand(),
        NoCommand(),
        WeatherCommand(),
        TemperatureCommand(),
        TimeCommand(),
        DateCommand(),
        SettingsCommand(),
        SearchCommand()
    )

    @JvmStatic
    fun main(args: Array<String>) {

        println("[VoiceAssistant] Starting WebDriver...")

        Configuration.create()

        keyword = Configuration.get("keyword")
        weatherUrl = Configuration.get("weather.com").replace("\\", "")
        email = Configuration.get("email")
        name = Configuration.get("name")

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