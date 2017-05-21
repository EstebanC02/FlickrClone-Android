#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_estebanmoncaleano_flickrclone_utilties_web_NetworkUtils_getFlickrAPIkey(JNIEnv *env,
                                                                                 jclass type) {

    return (*env)->NewStringUTF(env, "OGNjNmYwNmM4OWIzODNlMWFiMTMxNmZmMGI4NGQ3NjM=");
}