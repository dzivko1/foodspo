package altline.foodspo.data.error

sealed class AppException : Exception()

class NotConnectedException : AppException()
class ServiceUnavailableException : AppException()
class AccessDeniedException : AppException()
class NotFoundException : AppException()
class UnknownException(original: Exception) : AppException() {
    init {
        initCause(original)
    }
}
