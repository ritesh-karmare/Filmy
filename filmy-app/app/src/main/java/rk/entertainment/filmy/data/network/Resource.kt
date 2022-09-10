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

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val value: T) : Resource<T>()
    data class Error(val throwable: Throwable? = null, val errorMessage: String? = null) :
        Resource<Nothing>()
}

suspend fun <T> coroutineApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Resource<T> {

    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Logs.logException(throwable)

            when (throwable) {
                is SocketTimeoutException -> {
                    Resource.Error(throwable, ErrorType.TIMEOUT.displayName)
                }

                is IOException -> {
                    Resource.Error(throwable, ErrorType.NETWORK.displayName)
                }

                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    Resource.Error(throwable, errorResponse)
                }

                else -> {
                    Resource.Error(throwable, ErrorType.UNKNOWN.displayName)
                }
            }
        }
    }
}

fun <T> flowApiCall(apiCall: suspend () -> T): Flow<Resource<T>> {
    return flow {
        try {
            emit(Resource.Loading)
            emit(Resource.Success(apiCall.invoke()))
        } catch (throwable: Throwable) {
            Logs.logException(throwable)

            when (throwable) {
                is SocketTimeoutException -> {
                    emit(Resource.Error(throwable, ErrorType.TIMEOUT.displayName))
                }

                is IOException -> {
                    emit(Resource.Error(throwable, ErrorType.NETWORK.displayName))
                }

                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    emit(Resource.Error(throwable, errorResponse))
                }

                else -> {
                    emit(Resource.Error(throwable, ErrorType.UNKNOWN.displayName))
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
