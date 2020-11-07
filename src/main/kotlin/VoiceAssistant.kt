import backend.VoiceRecognition
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.HashMap
import kotlin.system.exitProcess

object VoiceAssistant {

    @JvmStatic
    lateinit var driver: ChromeDriver

    val keywords = listOf("hey blaze", "ok blaze")

    @JvmStatic
    fun main(args: Array<String>) {

        println("[VoiceAssistant] Starting WebDriver...")

        val prefs: MutableMap<String, Any> = HashMap()
        prefs["profile.default_content_setting_values.media_stream_mic"] = 1
        val options = ChromeOptions()
        options.setExperimentalOption("prefs", prefs)

        System.setProperty("webdriver.chrome.driver", "files/chromedriver.exe")
        driver = ChromeDriver(options)

        VoiceRecognition.startRecognition()

    }

    fun shutdown() {
        driver.quit()
        exitProcess(-1)
    }

}