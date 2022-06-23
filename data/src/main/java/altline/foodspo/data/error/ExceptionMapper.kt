package altline.foodspo.data.error

import altline.foodspo.data.network.NetworkUtils
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection


class ExceptionMapper(
    private val networkUtils: NetworkUtils
) {
    
    suspend operator fun <T> invoke(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: Exception) {
            throw mapException(e)
        }
    }
    
    private fun mapException(e: Exception): AppException {
        return when (e) {
            is IOException -> {
                if (networkUtils.hasInternetConnection()) UnknownException(e)
                else NotConnectedException()
            }
            is HttpException -> mapHttpError(e)
            else -> UnknownException(e)
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
