# 🌩JustWeather 

## Table of content
- [Project description](#project-description)
- [Screenshots](#screenshots)
- [Used technologies](#used-technologies)
- [Set up project](#set-up-the-project)
- [Project structure](#project-structure)
- [Download release apk](#download-release-apk)
- [License](#license)

## Project description
It's a weather app writed in Java. App asking permission to location and use it to detect weather in your current city (if you granted permission). You can add cities manually by writing them. The whole code and design was made by me. Design you can see <a href="https://www.figma.com/file/wSLUbKT9fAasHgdKyk5tz1/WeatherApp">here</a> in Figma. While making this project, I practiced my knowlege about databases, dependency injection, data binding, working with api and even some design skills.

## Screenshots
Currently project isn't ready, so do screenshots. They will be added as soon as possible.

## Used technologies
#### Libraries:
- <a href="https://developer.android.com/jetpack/androidx/releases/room">Room SQLite</a>
- <a href="https://github.com/google/dagger">Dagger2</a>
- <a href="https://developer.android.com/topic/libraries/data-binding">DataBinding</a>
- <a href="https://square.github.io/retrofit/">Retrofit2</a>

#### List of APIs
- [OpenWeatherMap](https://openweathermap.org) for getting weather info

## Set up the project
> The app **won't compile** without **keys.c**. For security concerns this file wasn't commit to repository. There are steps how to add this file below. 
- First of all, download or clone this repository
- Open this project and wait for Gradle Sync to complete
- Now you already have this app code, but for building it, you will need your own API-keys, because I can't push mine. </br>
To do this, please follow steps with screenshots below: </br>
#### Android Studio:
- Choose project view in left side pannel </br> <img src="https://i.imgur.com/BUhW029.png">
- Go to app/src/main/jni and create new file and name it keys.c </br> <img src="https://i.imgur.com/8gVVjXe.png?2"> <img src="https://i.imgur.com/QhxR3Zk.png">
- Copy paste to keys.c this code </br>

```C
#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_denbondd_justweather_services_Keys_getOWMkey(JNIEnv* env, jobject this) {
    return (*env)->NewStringUTF(env, "Your API-key for OpenWeatherMap");
}
```
>If you don't know how to get your own API-key, here you go </br>
> - <a href="https://OpenWeatherMap.org/api_keys">get key for OpenWeatherMap</a>
- Now build your project again, and if you paste working api key, you can use this app now.

## Project structure
- com.denbondd.justweather
    - dataflow - files for working with data (APIs)
    - db - files for working with RoomDB
    - di - files for dependency injection (Dagger2)
    - models - models needed for project
        - currentweatherowm - package with models for OpenWeatherMap CurrentWeather request
        - findowm - models for OpenWeatherMap FindCity request
        - onecallowm - models for OpenWeatherMap OneCall request
    - services - files for working with retrofit
    - ui - files used for user interface
        - activities - all activities of project
        - adapter - adapters and viewholders for recycler views
        - base - base ui components, that all other inherit
        - fragments - all fragments of project
    - util - different extensions     

## Download release apk
Release apk isn't available now, because I'm currently working on this app. Anyway, if you want to take a look on it, you can set up this project in Android Studio.

## License
Copyright (c) 2020 Denis Bondarenko </br>
[MIT License](./LICENSE)
