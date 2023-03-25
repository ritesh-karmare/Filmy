package rk.entertainment.data.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import rk.entertainment.data.BuildConfig.API_KEY


class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request: Request = chain.request()

        val url: HttpUrl = request.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
                .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}