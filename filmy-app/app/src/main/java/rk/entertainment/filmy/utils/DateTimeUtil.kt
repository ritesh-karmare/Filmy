package rk.entertainment.filmy.utils

import java.text.ParseException
import java.util.*

object DateTimeUtil {

    // The date should be of format yyyy-MM-dd  // Eg: 2018-10-29 -> outputs: 2018
    @JvmStatic
    fun getYearFromDate(releaseDate: String?): String? {
        return try {
            releaseDate?.substring(0, 4) ?: ""
        } catch (e: ParseException) {
            Logs.logException(e)
            releaseDate
        }
    }

    // Get the time in Hours and Minutes format from Total Number of Minutes
    // Egs: 123 minutes -> outputs: 2 hours 3 minutes
    @JvmStatic
    fun getHoursAndMinutes(totalMinutes: Int): String {
        val hours = totalMinutes / 60 //since both are ints, you get an int
        val minutes = totalMinutes % 60
        return String.format(Locale.getDefault(), "%d hours %02d minutes", hours, minutes)
    }
}