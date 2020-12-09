import backend.core.Configuration
import backend.core.VoiceRecognition
import backend.core.VoiceSynthesizer
import backend.commands.*
import backend.commands.google.SearchCommand
import backend.commands.system.LockCommand
import backend.commands.system.SettingsCommand
import backend.commands.weather.TemperatureCommand
import backend.commands.weather.WeatherCommand
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Select
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
    var pin = ""

    var locked = false

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
        SearchCommand(),
        LockCommand()
    )

    @JvmStatic
    fun main(args: Array<String>) {

        println("[VoiceAssistant] Starting WebDriver...")

        Configuration.create()

        keyword = Configuration.get("keyword")
        weatherUrl = Configuration.get("weather.com").replace("\\", "")
        email = Configuration.get("email")
        name = Configuration.get("name")
        pin = Configuration.get("pin")

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
        val voice = Select(driver.findElement(By.id("sprachwahl")))
        voice.selectByValue("Hans")

        if (Configuration.getFromOptions("setup") == "true") {
            startSetup()
        }

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

    private fun startSetup() {
        VoiceSynthesizer.speakText("Guten Tag sir, ich starte die Einrichtung. Soll ich neue Einstellungen importieren")
        checkAnswer()
    }

    private fun checkAnswer() {
        val answer = VoiceRecognition.startReactiveRecognition()

        when (answer.toLowerCase()) {
            "ja" -> {
                importSettings()
            }
            "nein" -> {
                VoiceSynthesizer.speakText("Okay")
            }
            else -> {
                VoiceSynthesizer.speakText("Ich konnte sie nicht verstehen, bitte wiederholen sie ihre Antwort")
                checkAnswer()
            }
        }
    }

    private fun importSettings() {
        VoiceSynthesizer.speakText("Aus welcher Datei soll ich die Einstellungen importieren")
        val answer = VoiceRecognition.startReactiveRecognition()

        VoiceSynthesizer.speakText("Importiere Einstellungen aus ${answer}.json")
        val import = Configuration.import(answer.toLowerCase())

        if (import) {
            VoiceSynthesizer.speakText("Import erfolgreich, Einstellungen wurden übernommen")
            Configuration.setOptionsValue("setup", "false")
        } else {
            VoiceSynthesizer.speakText("Das importieren aus ${answer}.json ist fehlgeschlagen, bitte versuchen sie es mit einer anderen Datei")
            importSettings()
        }

    }

}