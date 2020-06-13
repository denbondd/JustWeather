# 🌩JustWeather 

## Table of content
- [Used technologies](#used-technologies)
- [Set up project](#set-up-the-project)
- [Project structure](#project-structure)
- [Download release apk](#download-release-apk)
- [License](#license)

## Used technologies
#### Libraries:
- Dagger2 for dependency injection
- Glide for photo importing
- Retrofit2 for working with API

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
    - dataflow - files for working with data (APIs and databases)
    - di - files for dependency injection
    - models - models needed for project
        - onecallowm - package with models for OpenWeatherMap OneCallApi
    - services - files for working with retrofit
    - ui - files used for user interface
        - activities - all activities of project
        - base - base ui components, that all other inherit
        - fragments - all fragments of project
    - util - different extensions     

## Download release apk
Release apk isn't available now, because I'm currently working on this app. Anyway, if you want to take a look on it, you can set up this project in Android Studio.

## License
Copyright (c) 2020 Denis Bondarenko </br>
[MIT License](./LICENSE)
