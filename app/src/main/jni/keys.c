#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_denbondd_justweather_services_Keys_getOWMkey(JNIEnv* env, jobject this) {
    return (*env)->NewStringUTF(env, "76b7e21b9852c689a0ec030c40a2232c");
}