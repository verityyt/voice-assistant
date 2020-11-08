import backend.VoiceRecognition
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.interactions.Actions
import java.awt.Robot
import java.util.HashMap
import kotlin.system.exitProcess
import java.util.ArrayList


object VoiceAssistant {

    @JvmStatic
    lateinit var driver: ChromeDriver

    private lateinit var tabs: ArrayList<String>

    val keyword = "hey jarvis"

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