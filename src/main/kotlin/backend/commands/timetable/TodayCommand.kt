package backend.commands.timetable

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.TimetableFetcher

class TodayCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf(
        "was habe ich heute",
        "was habe ich heute für stunden",
        "welche stunden habe ich heute",
        "welche stunden habe ich heute in der schule",
        "was habe ich heute in der schule"
    )

    override fun perform(input: String) {
        val lessons = TimetableFetcher.getToday()

        if (lessons != null) {
            var answer = "Heute haben sie "

            var cur = 0

            for (lesson in lessons) {
                if (lesson.key != "label") {
                    if (cur != (lessons.size - 2)) {
                        answer += "${lesson.value}, "
                    } else {
                        answer += "und ${lesson.value}"
                    }
                    cur++
                }
            }

            VoiceSynthesizer.speakText(answer)
        } else {
            VoiceSynthesizer.speakText("Heute haben sie keine schule, sir")
        }

    }

    override var state: Int = 0

}