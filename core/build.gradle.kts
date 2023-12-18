import SharedDependencies.applySharedDeps
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

val baseUrlPropertiesFile by lazy { rootProject.file("baseurl.properties") }
val baseUrlProperties by lazy { Properties() }
baseUrlProperties.load(FileInputStream(baseUrlPropertiesFile))

android {
    namespace = AppInfo.coreModuleId
    compileSdk = AppInfo.compileSdkVersion

    defaultConfig {
        minSdk = AppInfo.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", baseUrlProperties.getProperty("BASE_URL"))
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
        buildConfig = true
    }
}

dependencies {
    applySharedDeps()

    //Retrofit
    implementation(Deps.SquareUp.retrofit)
    implementation(Deps.SquareUp.retrofitGson)
    implementation(Deps.SquareUp.okhttp3Logging)

    //Room
    implementation(Deps.Room.ktx)
    implementation(Deps.Room.runtime)
    kapt(Kapt.roomCompiler)

    //Data store
    implementation(Deps.dataStore)

    //implement other dependencies here..
}