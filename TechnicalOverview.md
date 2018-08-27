# Overview

<p align="center">
  <img src="https://media.giphy.com/media/3o7rc0qU6m5hneMsuc/giphy.gif" width="350" height="350"/> 
</p>

### The codebase has been developed maintaining standard guidelines; architecture and design patterns.<br> Considering future scaling of the application, the codebase is kept modular.

> Feel free to review the code. <br>  Feedbacks are always welcome!

# Enviroment

|                    |                         |
|  ----------------  |  ---------------------  |
|  **IDE**           |  Android Studio v3.1.4  |
|  **SDK**           |  Android SDK            |
|  **Build Tool**    |  v27.0.3                |
|  **Language**      |  Java, XML              |
|  **Architecture**  |  MVP                    |

# Compatibility

|      SDK           |      Version            |
|  :---------------  |  :--------------------  |
|  **Minimum**       |  16 (Android 4.1).      |
|  **Target**        |  27 (Android 8.1)       |


# Libraries

|      Name          |      Description        |
|  :---------------  |  :--------------------  |
|  1. **Retrofit**       |  REST Client. It makes it relatively easy to retrieve and upload JSON via a REST based webservice  |
|  2. **OkHttp**         |  Set timeouts and logging intercepters  |
|  3. **Gson**           |  Parse JSON to Java Objects and vice versa.  |
|  4. **RxJava**		     |  Enables reactive programming to handle asynchronous task and <br>avoid "Callback Hell" using Observable and                             Observer patterns.</br>  |
|  5. **Glide**		       |  Loads remote images effectively and provides caching mechanism for the same.  |
|  6. **ButterKnife**	   |  View binding framework. No more findViewById! <br>resulting in lesser code and lesser development time</br>  |
|  7. **Timber**		     |  Logging library, an abstraction over android's logging interface, <br>provides feature of enabling and                                  </br>disabling logs for development and release builds respectively.  |
|  8. **Crashlytics**    |  Track the crashes app-wide  |


# UI Components

|      Component     |      Purpose            |
|  :---------------  |  :--------------------  |
|  1. **RecyclerView**            |  To display the data list                 |
|  2. **SwipeRefreshLayout**      |  To refresh and to load more data         |
|  3. **TabLayout**               |  To swipe across various movie category   |
|  4. **CardView**                |  Container for the movie item             |
|  5. **ViewPager**               |  To display list of images                |
|  6. **CollapsingToolbarLayout** |  To contain viewpager and parallex effect |


# Technical implementation pipeline

1. Dagger as dependency injection
2. Architecture improvement
3. Unit Testing using JUnit & Espresso


# Credits & References 

 [Retrofit](https://www.journaldev.com/13639/retrofit-android-example-tutorial)
<br> [RxJava](https://www.toptal.com/android/functional-reactive-android-rxjava)
<br> [Glide](https://github.com/codepath/android_guides/wiki/Displaying-Images-with-the-Glide-Library)
<br> [ButterKnife](http://jakewharton.github.io/butterknife/)
<br> [Timber](https://medium.com/@caueferreira/timber-enhancing-your-logging-experience-330e8af97341)
<br> [Crashlytics](https://fabric.io/kits/android/crashlytics)
<br> [Securing ````API_KEY````](https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906)
<br> [Semantic App Versioning](https://medium.com/@maxirosson/versioning-android-apps-d6ec171cfd82)
<br><br> and whatever seems helpful...

<br>
<br>
<br>
<br>

<p align="center">
<img src="https://media.giphy.com/media/DAtJCG1t3im1G/giphy.gif"/>
</p>
