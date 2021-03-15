package rk.entertainment.filmy.data.repository

import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rk.entertainment.filmy.BuildConfig
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object APIClient {
    private var retrofit: Retrofit? = null
    private var logging: HttpLoggingInterceptor? = null
    private var okHttpClient: OkHttpClient? = null

    // okHttpClient = getUnsafeOkHttpClient();
    fun getClient(): Retrofit? {
        if (logging == null) {
            logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                logging!!.level = HttpLoggingInterceptor.Level.BODY
            else
                logging!!.level = HttpLoggingInterceptor.Level.NONE
        }
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(RequestInterceptor())
                    .addInterceptor(logging!!)
                    .build()

            // okHttpClient = getUnsafeOkHttpClient();
        }
        if (retrofit == null) retrofit = Retrofit.Builder()
                .baseUrl(APIUtils.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }

    // Create a trust manager that does not validate certificate chains
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {

        // Install the all-trusting trust manager

        // Create an ssl socket factory with our all-trusting manager
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            TLS_DHE_RSA_WITH_AES_128_GCM_SHA256).build()
            builder.connectionSpecs(listOf(spec))
            return builder
        } catch (e: Exception) {
            Timber.e(e)
        }
        return OkHttpClient.Builder()
    }
}