package rk.entertainment.filmy.data.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rk.entertainment.filmy.BuildConfig
import java.util.concurrent.TimeUnit

object APIClient {


    private val retrofitClient: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logging.level = HttpLoggingInterceptor.Level.BODY
        else
            logging.level = HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(RequestInterceptor())
                .addInterceptor(logging)
                .build()

        Retrofit.Builder()
                .baseUrl(APIUtils.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val getAPIService: APIService by lazy {
        retrofitClient.create(APIService::class.java)
    }
}