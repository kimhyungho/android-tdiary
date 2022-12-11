plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.HILT_PLUGIN)
    id(Plugins.GOOGLE_SERVICE)
    id(Plugins.SECRETS_GRADLE_PLUGIN)
    id(Plugins.CRASHLYTICS)
    id(Plugins.NAVIGATION_SAFEARGS)
    kotlin(Plugins.PARCELIZE)
}

android {
    namespace = "com.hardy.yongbyung"
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    signingConfigs {
        getByName("debug") {
            keyAlias = getPropertyValue("yongbyung.debug.keyAlias")
            keyPassword = getPropertyValue("yongbyung.debug.keyPassword")
            storeFile = file("../DebugKeyStore.jks")
            storePassword = getPropertyValue("yongbyung.debug.storePassword")
        }

        create("yongbyung") {
            keyAlias = getPropertyValue("yongbyung.prod.keyAlias")
            keyPassword = getPropertyValue("yongbyung.prod.keyPassword")
            storeFile = file("../YongByungKeyStore")
            storePassword = getPropertyValue("yongbyung.prod.storePassword")
        }
    }

    defaultConfig {
        applicationId = "com.hardy.yongbyung"
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            signingConfig = signingConfigs.getByName("yongbyung")
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

    buildFeatures {
        dataBinding = true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }
}

fun getPropertyValue(propertyKey: String): String {
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty(propertyKey)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // hilt
    kapt(Dependencies.KAPT_HILT_COMPILER)
    implementation(Dependencies.HILT_ANDROID)

    testImplementation(Testing.JUNIT4)
    androidTestImplementation(Testing.ANDROID_JUNIT)
    androidTestImplementation(Testing.ESPRESSO_CORE)

    debugImplementation(Dependencies.LEAKCANARY)
    implementation(Dependencies.LEAKCANARY_PLUMBER)

    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)

    implementation(Dependencies.FRAGMENT_KTX)

    implementation(Dependencies.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.LIFECYCLE_LIVEDATA)

    implementation(Dependencies.FIREBASE_ANALYTICS)

    kapt(Dependencies.GLIDE_COMPILER)
    implementation(Dependencies.GLIDE)

    implementation(Dependencies.OPENCSV)

    implementation(Dependencies.LOTTIE)

    implementation(Dependencies.SWIPE_REFRESH_LAYOUT)

    implementation(Dependencies.SPLASH_SCREEN)

    implementation("com.google.firebase:firebase-messaging-ktx:23.1.0")

    implementation("io.github.ParkSangGwon:tedpermission-coroutine:3.3.0")
}