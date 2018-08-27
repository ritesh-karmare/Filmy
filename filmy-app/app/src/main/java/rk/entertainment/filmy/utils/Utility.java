package rk.entertainment.filmy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import rk.entertainment.filmy.data.models.moviesDetails.GenreData;
import rk.entertainment.filmy.data.models.moviesDetails.ProductionCompanyData;
import timber.log.Timber;

public class Utility {

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getAppendedStringFromList(List<?> objList) {

        String nameStr = null;

        if (objList != null && objList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < objList.size(); i++) {

                Object data = objList.get(i);
                if (data instanceof GenreData) {
                    stringBuilder.append(((GenreData) data).getName());
                } else if (data instanceof ProductionCompanyData) {
                    stringBuilder.append(((ProductionCompanyData) data).getName());
                }
                //if the value is not the last element of the list, then append the comma(,) as well
                if (i != objList.size() - 1)
                    stringBuilder.append(", ");
            }
            nameStr = stringBuilder.toString();
        }
        return nameStr;
    }

    public static String getYearFromDate(String releaseDate) {

        if (releaseDate == null || releaseDate.isEmpty())
            return "";

        String releaseYear = null;
        Calendar mydate = new GregorianCalendar();
        try {
            Date thedate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate);
            mydate.setTime(thedate);
            releaseYear = String.valueOf(mydate.get(Calendar.YEAR));
        } catch (ParseException e) {
            Timber.e(Utility.getExceptionStrign(e));
            releaseYear = releaseDate;
        }
        return releaseYear;
    }

    public static String getHoursAndMinutes(int totalMinutes) {

        int hours = totalMinutes / 60; //since both are ints, you get an int
        int minutes = totalMinutes % 60;

        return String.format(Locale.getDefault(), "%d hours %02d minutes", hours, minutes);
    }

    public static String getExceptionStrign(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
