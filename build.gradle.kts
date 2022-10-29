buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    }
}
plugins {
    id(Plugins.ANDROID_APPLICATION) version "7.3.0" apply false
    id(Plugins.ANDROID_LIBRARY) version "7.3.0" apply false
    id(Plugins.DAGGER_HILT) version "2.43.2" apply false
    id(Plugins.SECRETS_GRADLE_PLUGIN) version "2.0.1" apply false
    id(Plugins.PROTOBUF) version "0.8.17" apply false
    id(Plugins.NAVIGATION_SAFEARGS) version "2.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}