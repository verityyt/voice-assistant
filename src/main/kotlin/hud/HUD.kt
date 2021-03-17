package hud

import backend.core.Configuration
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.sun.jna.NativeLibrary
import org.slf4j.LoggerFactory
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent
import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JDialog


object HUD {

    private lateinit var boxWindow: Window
    private val videoWindow = JDialog()
    private lateinit var playerComp: EmbeddedMediaPlayerComponent

    private var screenWidth = Toolkit.getDefaultToolkit().screenSize.width

    var isShown = true

    fun startup() {

        /* BOX */

        boxWindow = object : Window(null) {
            override fun paint(g: Graphics) {
                if (graphics != null) {
                        val g2d = graphics as Graphics2D

                    g2d.setRenderingHint(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                    )
                    g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                    )
                    g2d.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                    )
                    g2d.setRenderingHint(
                        RenderingHints.KEY_STROKE_CONTROL,
                        RenderingHints.VALUE_STROKE_NORMALIZE
                    )
                    g2d.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR
                    )
                    g2d.setRenderingHint(
                        RenderingHints.KEY_FRACTIONALMETRICS,
                        RenderingHints.VALUE_FRACTIONALMETRICS_ON
                    )

                    g2d.drawImage(ImageIO.read(File("files/images/HUD.png")), screenWidth - 386 - 25, 25, 386, 238, this)

                }
            }
        }

        boxWindow.isAlwaysOnTop = true
        boxWindow.bounds = boxWindow.graphicsConfiguration.bounds
        boxWindow.background = Color(0, true)
        boxWindow.isVisible = true

        /* VIDEO */

        NativeLibrary.addSearchPath("libvlc", "E:\\Program Files\\VLC")
        System.setProperty("VLC_PLUGIN_PATH", Configuration.getFromOptions("vlc"))

        val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        rootLogger.level = Level.toLevel("off")

        val file = File("files/videos/waiting.mp4")

        playerComp = EmbeddedMediaPlayerComponent()
        videoWindow.add(playerComp)

        videoWindow.isUndecorated = true
        videoWindow.isVisible = true
        videoWindow.isResizable = false
        videoWindow.size = Dimension(180, 180)
        videoWindow.isAlwaysOnTop = true

        videoWindow.move(screenWidth - 180 - 130, 50)

        playerComp.mediaPlayer.repeat = true
        playerComp.mediaPlayer.scale = 0.2f
        playerComp.mediaPlayer.playMedia(file.absolutePath)

    }

    fun show() {
        boxWindow.isVisible = true
        playerComp.mediaPlayer.play()
        videoWindow.isVisible = true

        isShown = true
    }

    fun hide() {
        boxWindow.isVisible = false
        playerComp.mediaPlayer.pause()
        videoWindow.isVisible = false

        isShown = false
    }

    fun play(video: String) {
        val file = File("files/videos/$video.mp4")
        playerComp.mediaPlayer.playMedia(file.absolutePath)
    }

}