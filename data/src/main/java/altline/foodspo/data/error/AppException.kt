package altline.foodspo.data.error

sealed class AppException : Exception()

class NotConnectedException : AppException()
class ServiceUnavailableException : AppException()
class AccessDeniedException : AppException()
class NotFoundException : AppException()
class UnknownException(original: Throwable) : AppException() {
    init {
        initCause(original)
    }
}
