import backend.commands.*
import backend.commands.tasks.SetupTask
import backend.core.*
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.Select
import kotlin.system.exitProcess

object Jarvis {

    @JvmStatic
    lateinit var driver: ChromeDriver

    var keyword = ""
    var weatherUrl = ""
    var email = ""
    var name = ""
    var pin = ""

    var locked = false

    val commands = listOf<VoiceCommand>(
        SetupTask(),
        ByeCommand()
        /*IntroduceCommand(),
        GreetingCommand(),
        ImBackCommand(),
        StopCommand(),
        ShutdownCommand(),
        NoCommand(),
        WeatherCommand(),
        TemperatureCommand(),
        TimeCommand(),
        DateCommand(),
        SettingsCommand(),
        SearchCommand(),
        LockCommand()*/
    )

    @JvmStatic
    fun main(args: Array<String>) {

        Logger.info("Starting up voice assistant...", this.javaClass.name)

        Configuration.create()

        keyword = Configuration.get("keyword")
        weatherUrl = Configuration.get("weather.com").replace("\\", "")
        email = Configuration.get("email")
        name = Configuration.get("name")
        pin = Configuration.get("pin")

        val options = ChromeOptions()
        options.setHeadless(true)
        System.setProperty("webdriver.chrome.driver", "files/chromedriver.exe")
        driver = ChromeDriver(options)

        driver.get("https://ttsmp3.com/text-to-speech/German/")
        val voice = Select(driver.findElement(By.id("sprachwahl")))
        voice.selectByValue("Hans")

        VoiceRecognizer.startup()

        if(Configuration.getFromOptions("setup") == "true") {
            startSetup()
        }else {
            VoiceSynthesizer.speakText("Alle Systeme startklar. Ich bin wieder online.")
        }

    }

    fun shutdown() {
        driver.quit()
        VoiceRecognizer.shutdown()
        exitProcess(-1)
    }

    private fun startSetup() {
        println("Starting setup...")
        VoiceRecognizer.activated = true
        VoiceRecognizer.currentCommand = SetupTask()
        (VoiceRecognizer.currentCommand as SetupTask).perform("")
    }

    /*private fun importSettings() {
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

    }*/

}