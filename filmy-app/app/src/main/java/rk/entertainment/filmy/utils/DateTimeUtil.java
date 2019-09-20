package rk.entertainment.filmy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtil {

    // The date should be of format yyyy-MM-dd
    // Eg: 2018-10-29 -> outputs: 2018
    public static String getYearFromDate(String releaseDate) {
        try {
            if (releaseDate == null || releaseDate.isEmpty()) return "";
            Calendar myDate = new GregorianCalendar();
            Date theDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate);
            myDate.setTime(theDate);
            return String.valueOf(myDate.get(Calendar.YEAR));
        } catch (ParseException e) {
e.printStackTrace();
            return releaseDate;
        }
    }

    // Get the time in Hours and Minutes format from Total Number of Minutes
    // Egs: 123 minutes -> outputs: 1 hours 3 minutes
    public static String getHoursAndMinutes(int totalMinutes) {
        int hours = totalMinutes / 60; //since both are ints, you get an int
        int minutes = totalMinutes % 60;
        return String.format(Locale.getDefault(), "%d hours %02d minutes", hours, minutes);
    }
}