import backend.commands.*
import backend.commands.DateCommand
import backend.commands.google.SearchCommand
import backend.commands.system.LockCommand
import backend.commands.system.SettingsCommand
import backend.commands.tasks.SetupTask
import backend.commands.weather.TemperatureCommand
import backend.commands.weather.WeatherCommand
import backend.core.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import kotlin.system.exitProcess

object VoiceAssistant {

    @JvmStatic
    lateinit var driver: ChromeDriver

    var keyword = ""
    var weatherUrl = ""
    var email = ""
    var name = ""
    var pin = ""
    var voice = ""
    var alias = ""
    var username = ""

    var locked = false

    val commands = listOf(
        SetupTask(),
        DateCommand(),
        ByeCommand(),
        GreetingCommand(),
        ImBackCommand(),
        IntroduceCommand(),
        NoCommand(),
        ShutdownCommand(),
        StopCommand(),
        TimeCommand(),
        WeatherCommand(),
        TemperatureCommand(),
        LockCommand(),
        SettingsCommand(),
        SearchCommand(),
        JokeCommand()
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

        driver.get("https://www.naturalreaders.com/online/")

        val speaker = driver.findElementByClassName("btn-speaker")
        speaker.click()

        Thread.sleep(2000)

        if (this.voice == "female") {
            driver.findElementByXPath("//div[text()='German - Claudia']").click()
        } else {
            driver.findElementByXPath("//div[text()='German - Klaus']").click()

            val speed = driver.findElementByClassName("btn-speed")
            speed.click()

            val items = driver.findElementsByClassName("speed-menu-item")

            for (item in items) {
                if (item.text == "0") {
                    item.click()
                }
            }
        }

        if (Configuration.getFromOptions("setup") == "true") {
            VoiceRecognizer.startup()
            startSetup()
        } else {
            VoiceSynthesizer.speakText("Alle Systeme startklar. Ich bin wieder online.")
            VoiceRecognizer.startup()
        }

    }

    fun shutdown() {
        driver.quit()
        VoiceRecognizer.shutdown()
        exitProcess(-1)
    }

    private fun startSetup() {
        println("Starting setup...")

        if (Configuration.getFromOptions("filename") != "default") {

            val input = Configuration.getFromOptions("filename")
            VoiceSynthesizer.speakText("Importiere Einstellungen aus ${input}.json")
            val import = Configuration.import(input)

            if (import) {
                VoiceSynthesizer.speakText("Import erfolgreich, Einstellungen wurden übernommen")
                Configuration.setOptionsValue("setup", "false")

                VoiceSynthesizer.speakText("Alle Systeme startklar. Ich bin wieder online.")
            } else {
                VoiceSynthesizer.speakText("Das importieren aus ${input}.json ist fehlgeschlagen, bitte versuchen sie es mit dem befehl \"Importiere neue einstellungen\" erneut")
            }

        } else {
            VoiceRecognizer.activated = true
            VoiceRecognizer.currentCommand = SetupTask()
            (VoiceRecognizer.currentCommand as SetupTask).perform("")
        }
    }

}