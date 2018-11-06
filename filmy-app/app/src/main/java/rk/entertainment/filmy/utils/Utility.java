package rk.entertainment.filmy.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import rk.entertainment.filmy.models.moviesDetails.GenreData;
import rk.entertainment.filmy.models.moviesDetails.ProductionCompanyData;

public class Utility {

    // Get the comma separated string from a lost of object
    // eg: get the comma separated string of Genre/Production companies from the list.
    public static String getAppendedStringFromList(List<?> objList) {
        String nameStr = null;
        if (objList != null && objList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < objList.size(); i++) {
                Object data = objList.get(i);
                if (data instanceof GenreData)
                    stringBuilder.append(((GenreData) data).getName());
                else if (data instanceof ProductionCompanyData)
                    stringBuilder.append(((ProductionCompanyData) data).getName());

                //if the value is not the last element of the list,
                // then append the comma(,) as well
                if (i != objList.size() - 1)
                    stringBuilder.append(", ");
            }
            nameStr = stringBuilder.toString();
        }
        return nameStr;
    }

    // Convert the exception object to string as a PrintStackTrsce
    public static String getExceptionString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
