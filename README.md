# Filmy 

[![Version](https://img.shields.io/badge/version-v5.0.0-brightgreen.svg)](https://play.google.com/store/apps/details?id=rk.entertainment.filmy)
[![License](https://img.shields.io/badge/license-GNU_GPLv3-blue.svg)](https://raw.githubusercontent.com/ritesh-karmare/Test/master/LICENSE)

![](/screenshots/filmy_banner.png)

Filmy: The fastest and easiest way to discover movies on your device. All the movie data is retrieved using [The Movie Database (TMDb)][1] APIs

[1]: http://slashdot.org

# Download

[<img src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' alt='Get it on Google Play' width='210' height='80'>](https://play.google.com/store/apps/details?id=rk.entertainment.filmy)

# [Technical trailer](/TechnicalOverview.md)

# Goal

* To showcase the implementation of:
  1. 3rd party libraries in Android that includes: Retrofit, OkHttp, Glide, Hilt, etc...
  2. Clean Architecture with Kotlin & MVVM

* To achieve this, I am using TMDb (The Movie Database) as a source to retrieve the data (movie information) over the network via REST APIs.
* Therefore, this project can be considered as a ***concept project***.


# Features 

* Categorized list of Now playing, Upcoming, Top Rated, Popular movies.
* Movie rating
* Movie details along with backdrop images(movie scenes)
* Search for a movie
* Watch trailers
* Recommended movies

# Change Log

* **v5.0.0 - 12 September, 2022** <br>
  * Recommended Movies'

* v2.0.0 - 06 November, 2018 <br>
  * Now you can watch 'trailers'

* v1.0.0 - 28 August, 2018 <br>
  * Categorized list of Now playing, Upcoming, Top Rated, Popular movies.
  * Movie rating
  * Movie details along with backdrop images(movie scenes)
  * Search for a movie



# TMDb Usage

* Register and get the API_KEY to retrieve the data via REST API.
* As per terms of use, its mandatory to properly attribute TMDb as the source and shall use the TMDb logo to identify your use of the TMDb APIs
* Request limits to 40 requests every 10 seconds.


# Author

Ritesh Karmare<br>
[<img src='https://cdnjs.cloudflare.com/ajax/libs/webicons/2.0.0/webicons/webicon-linkedin.svg' alt='Get it on Google Play' width='32' height='32'>](https://www.linkedin.com/in/riteshkarmare/)<br>



# License

```
Copyright (C) 2018 Ritesh Karmare

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```
