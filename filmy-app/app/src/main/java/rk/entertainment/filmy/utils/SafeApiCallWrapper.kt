package rk.entertainment.filmy.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> callSafeApi(emitter: RemoteErrorEmitter, responseFunction: suspend () -> T): T? {
    return try {
        val response = withContext(Dispatchers.IO + exceptionHandler) { responseFunction.invoke() }
        response
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Timber.e(e)
            when (e) {
                is HttpException -> {
                    val body = e.response()?.errorBody()
                    emitter.onError(getErrorMessage(body))
                }
                is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT.displayName)
                is IOException -> emitter.onError(ErrorType.NETWORK.displayName)
                else -> emitter.onError(ErrorType.UNKNOWN.displayName)
            }
        }
        null
    }
}

val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Timber.e(throwable)
}

interface RemoteErrorEmitter {
    fun onError(msg: String)
}

enum class ErrorType(val displayName: String) {
    NETWORK("Network error"), // IO
    TIMEOUT("Connection timed out. Please check your connection."), // Socket
    UNKNOWN("Something went wrong") //Anything else
}


const val MESSAGE_KEY = "status_message"
const val ERROR_KEY = "error"

fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val jsonObject = JSONObject(responseBody!!.string())
        when {
            jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
            jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
            else -> "Something went wrong"
        }
    } catch (e: Exception) {
        Timber.e(e)
        "Something went wrong"
    }
}