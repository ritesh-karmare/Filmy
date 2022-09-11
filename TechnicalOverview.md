# Overview

<p align="center">
  <img src="https://media.giphy.com/media/3o7rc0qU6m5hneMsuc/giphy.gif" width="350" height="350"/> 
</p>

#### The codebase has been developed maintaining standard guidelines; architecture and design patterns. Considering future scaling of the application, the codebase is kept modular.

> Feel free to review the code. <br>  Feedbacks are always welcome!


# Project Structure

<p align="center">
  <img src="/screenshots/project_structure.png"/> 
</p>


# Enviroment

|                    |                              |
|  ----------------  |  --------------------------  |
|  **IDE**           |  Android Studio v3.1.4       |
|  **SDK**           |  Android SDK                 |
|  **Build Tool**    |  v27.0.3                     |
|  **Language**      |  Kotlin, XML                 |
|  **Architecture**  |  Clean Architecture + MVVM   |

# Compatibility

|      SDK           |      Version            |
|  :---------------  |  :--------------------  |
|  **Minimum**       |  Android 21             |
|  **Target**        |  Android 32             |


# Libraries

|      Name          |      Description        |
|  :---------------  |  :--------------------  |
|  1. **Retrofit**       |  REST Client. It makes it relatively easy to retrieve and upload JSON via a REST based webservice  |
|  2. **OkHttp**         |  Set timeouts and logging intercepters  |
|  3. **Gson**           |  Parse JSON to Java Objects and vice versa.  |
|  4. **Glide**		       |  Loads remote images effectively and provides caching mechanism for the same.  |
|  5. **Hilt**		       |  Reduces the boilerplate code of doing manual dependency injection.  |
|  6. **Crashlytics**    |  Track the crashes app-wide  |
|  7. **Firebase Analytics**    |  Track the analytics of the app usage and behaviour  |
|  8. **Firebase Performance**    |  Track app performance  |
|  9. **Youtube Data v3**    |  To play youtube videos  |


# UI Components

|      Component     |      Purpose            |
|  :---------------  |  :--------------------  |
|  1. **RecyclerView**            |  To display the data list                 |
|  2. **SwipeRefreshLayout**      |  To refresh and to load more data         |
|  3. **TabLayout**               |  To swipe across various movie category   |
|  4. **CardView**                |  Container for the movie item             |
|  5. **ViewPager**               |  To display list of images                |
|  6. **CollapsingToolbarLayout** |  To contain viewpager and parallex effect |


# Credits & References 

 [Retrofit](https://www.journaldev.com/13639/retrofit-android-example-tutorial)
<br> [RxJava](https://www.toptal.com/android/functional-reactive-android-rxjava)
<br> [Glide](https://github.com/codepath/android_guides/wiki/Displaying-Images-with-the-Glide-Library)
<br> [ButterKnife](http://jakewharton.github.io/butterknife/)
<br> [Timber](https://medium.com/@caueferreira/timber-enhancing-your-logging-experience-330e8af97341)
<br> [Crashlytics](https://fabric.io/kits/android/crashlytics)
<br> [Securing ````API_KEY````](https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906)
<br> [Semantic App Versioning](https://medium.com/@maxirosson/versioning-android-apps-d6ec171cfd82)
<br><br> and whatever seemed helpful...

<br>
<br>

<p align="center">
<img src="https://media.giphy.com/media/DAtJCG1t3im1G/giphy.gif"/>
</p>
