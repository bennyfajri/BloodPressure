import SharedDependencies.applySharedDeps

plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}



android {
    namespace = AppInfo.applicationId
    compileSdk = AppInfo.compileSdkVersion

    defaultConfig {
        applicationId = AppInfo.applicationId
        minSdk = AppInfo.minSdkVersion
        targetSdk = AppInfo.targetSdkVersion
        versionCode = AppInfo.versionCode
        versionName = AppInfo.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(mapOf("path" to ":core")))
    applySharedDeps()

    implementation(Deps.mpAndroidChart)

    //implement other dependencies here..
}