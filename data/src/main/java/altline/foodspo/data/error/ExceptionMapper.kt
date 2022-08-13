package altline.foodspo.data.error

import altline.foodspo.data.network.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ExceptionMapper @Inject constructor(
    private val networkUtils: NetworkUtils
) {
    
    suspend operator fun <T> invoke(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: Exception) {
            throw mapThrowable(e)
        }
    }
    
    fun <T> forFlow(flow: Flow<T>) = flow.catch { throw mapThrowable(it) }
    
    private fun mapThrowable(throwable: Throwable): AppException {
        return when (throwable) {
            is AppException -> throw throwable
            is IOException -> {
                if (networkUtils.hasInternetConnection()) UnknownException(throwable)
                else NotConnectedException()
            }
            is HttpException -> mapHttpError(throwable)
            is Exception -> UnknownException(throwable)
            else -> throw throwable
        }
    }
    
    private fun mapHttpError(httpException: HttpException): AppException {
        return when (httpException.code()) {
            HttpURLConnection.HTTP_UNAVAILABLE -> ServiceUnavailableException()
            HttpURLConnection.HTTP_UNAUTHORIZED -> AccessDeniedException()
            HttpURLConnection.HTTP_FORBIDDEN -> AccessDeniedException()
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            else -> UnknownException(httpException)
        }
    }
}
