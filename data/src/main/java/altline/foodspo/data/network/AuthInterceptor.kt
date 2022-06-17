package altline.foodspo.data.network

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val processedRequest = chain.request().newBuilder()
            .addHeader("x-api-key", "25a33bc345994af7b863333bef27216d")
            .build()
        
        return chain.proceed(processedRequest)
    }
}