object Dependencies {


    // Support dependencies
    const val multiDex = "com.android.support:multidex:1.0.3"
    const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.3.0"
    const val material = "com.google.android.material:material:1.6.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    // OkHttp, Retrofit and Gson networking libraries
    const val okHttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
    const val ohHttpLogginInterceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
    const val rxJava = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

    // { exclude module : 'okhttp' }
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val gson = "com.google.code.gson:gson:2.9.1"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:4.13.2"
    const val glideCompilerKapt = "com.github.bumptech.glide:compiler:4.13.2"

    // Firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:26.6.0"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics"
    const val firebasePref = "com.google.firebase:firebase-perf"

    // Youtube Player
    //implementation files('libs/YouTubeAndroidPlayerApi.jar')
    const val youtubePlayer = "libs/YouTubeAndroidPlayerApi.jar"

    // Coroutines, ViewModel, LiveData
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.6.0"
    const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.0"

    // hilt
    const val hilt = "com.google.dagger:hilt-android:2.44"
    const val hiltCompilerKapt = "com.google.dagger:hilt-compiler:2.44"


}