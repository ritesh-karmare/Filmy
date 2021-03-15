package rk.entertainment.filmy.utils

import rk.entertainment.filmy.data.models.moviesDetails.GenreData
import rk.entertainment.filmy.data.models.moviesDetails.ProductionCompanyData

object Utility {

    // Get the comma separated string from a list of object
    // eg: get the comma separated string of Genre/Production companies from the list.

    @JvmStatic
    fun getAppendedStringFromList(objList: List<*>?): String? {

        var nameStr: String? = null
        if (objList != null && objList.isNotEmpty()) {
            val stringBuilder = StringBuilder()
            for (i in objList.indices) {
                val data = objList[i]!!
                if (data is GenreData) stringBuilder.append(data.name)
                else if (data is ProductionCompanyData)
                    stringBuilder.append(data.name)

                //if the value is not the last element of the list, then append the comma(,) as well
                if (i != objList.size - 1) stringBuilder.append(", ")
            }
            nameStr = stringBuilder.toString()
        }
        return nameStr
    }
}