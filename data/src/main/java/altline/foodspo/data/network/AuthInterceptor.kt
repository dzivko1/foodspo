package altline.foodspo.data.network

import altline.foodspo.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val processedRequest = chain.request().newBuilder()
            .addHeader("x-api-key", BuildConfig.SPOONACULAR_API_KEY)
            .build()
        
        return chain.proceed(processedRequest)
    }
}