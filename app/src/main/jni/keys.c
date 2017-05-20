#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_estebanmoncaleano_flickrclone_utilities_NetworkUtils_getFlickrAPIkey(JNIEnv *env) {
    return (*env)->NewStringUTF(env, "OGNjNmYwNmM4OWIzODNlMWFiMTMxNmZmMGI4NGQ3NjM=");
}