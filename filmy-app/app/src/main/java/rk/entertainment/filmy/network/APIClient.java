package rk.entertainment.filmy.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rk.entertainment.filmy.BuildConfig;

public class APIClient {

    private static Retrofit retrofit = null;
    private static HttpLoggingInterceptor logging = null;
    private static OkHttpClient okHttpClient = null;

    public static Retrofit getClient() {
        if (logging == null) {
            logging = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG)
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            else
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        if (okHttpClient == null)
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();


        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIUtils.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }
}
