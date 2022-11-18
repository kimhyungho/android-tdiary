import com.google.protobuf.gradle.*

plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.SECRETS_GRADLE_PLUGIN)
    id(Plugins.PROTOBUF)
}

android {
    namespace = "com.hardy.data"
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            dimension = "environment"
        }
        create("prod") {
            dimension = "environment"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.PROTOBUF}"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))

    testImplementation(Testing.JUNIT4)
    androidTestImplementation(Testing.ANDROID_JUNIT)
    androidTestImplementation(Testing.ESPRESSO_CORE)

    kapt(Dependencies.KAPT_HILT_COMPILER)
    implementation(Dependencies.HILT_ANDROID)

    //firebase database
    implementation(Dependencies.FIRESTORE)
    implementation(Dependencies.FIREBASE_DATABASE)

    implementation(Dependencies.PREFERENCES_DATASTORE)
    implementation(Dependencies.PROTO_DATASTORE)
    implementation(Dependencies.PROTOBUF)

    implementation(Dependencies.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.GSON)
    implementation(Dependencies.OKHTTP_INTERCEPTOR)

    kapt(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)

    implementation (Dependencies.FIREBASE_DATABASE)

    implementation(Dependencies.FIREBASE_STORAGE)

}