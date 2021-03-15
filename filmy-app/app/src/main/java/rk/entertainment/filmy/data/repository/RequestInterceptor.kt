package rk.entertainment.filmy.data.repository

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request: Request = chain.request()

        val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", APIUtils.API_KEY)
                .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}