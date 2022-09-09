package rk.entertainment.filmy.data.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import rk.entertainment.filmy.utils.Logs
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by Ritesh
 * on 31/07/21
 */

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val throwable: Throwable? = null, val errorMessage: String? = null) :
        ResultWrapper<Nothing>()
}

suspend fun <T> coroutineApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {

    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Logs.logException(throwable)

            when (throwable) {
                is SocketTimeoutException -> {
                    ResultWrapper.Error(throwable, ErrorType.TIMEOUT.displayName)
                }

                is IOException -> {
                    ResultWrapper.Error(throwable, ErrorType.NETWORK.displayName)
                }

                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.Error(throwable, errorResponse)
                }

                else -> {
                    ResultWrapper.Error(throwable, ErrorType.UNKNOWN.displayName)
                }
            }
        }
    }
}

fun <T> flowApiCall(apiCall: suspend () -> T): Flow<ResultWrapper<T>> {
    return flow {
        try {
            emit(ResultWrapper.Loading)
            emit(ResultWrapper.Success(apiCall.invoke()))
        } catch (throwable: Throwable) {
            Logs.logException(throwable)

            when (throwable) {
                is SocketTimeoutException -> {
                    emit(ResultWrapper.Error(throwable, ErrorType.TIMEOUT.displayName))
                }

                is IOException -> {
                    emit(ResultWrapper.Error(throwable, ErrorType.NETWORK.displayName))
                }

                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    emit(ResultWrapper.Error(throwable, errorResponse))
                }

                else -> {
                    emit(ResultWrapper.Error(throwable, ErrorType.UNKNOWN.displayName))
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.source()?.toString()
    } catch (exception: Exception) {
        null
    }
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
            else -> ErrorType.UNKNOWN.displayName
        }
    } catch (e: Exception) {
        Logs.logException(e)
        responseBody?.source()?.toString() ?: ErrorType.UNKNOWN.displayName
    }
}
