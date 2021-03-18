package backend.commands.timetable

import backend.core.VoiceCommand
import backend.core.VoiceSynthesizer
import backend.utils.TimetableFetcher

class TomorrowCommand : VoiceCommand() {

    override val needsReaction: Boolean = false

    override val keywords: List<String> = listOf(
        "was habe ich morgen",
        "was habe ich morgen für stunden",
        "welche stunden habe ich morgen",
        "welche stunden habe ich morgen in der schule",
        "was habe ich morgen in der schule"
    )

    override fun perform(input: String) {
        val lessons = TimetableFetcher.getTomorrow()

        var answer = "Morgen haben sie "

        var cur = 0

        for (lesson in lessons) {
            if (lesson.key != "label") {
                if(cur != (lessons.size - 2)) {
                    answer += "${lesson.value}, "
                }else {
                    answer += "und ${lesson.value}"
                }
                cur++
            }
        }

        VoiceSynthesizer.speakText(answer)

    }

    override var state: Int = 0

}