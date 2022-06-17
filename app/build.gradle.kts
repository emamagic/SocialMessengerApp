plugins {
    id("com.android.application")
    kotlin ("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

apply {
    from("$rootDir/android-core-build.gradle")
}


android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.applicationId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Releases.versionCode
        versionName = Releases.versionName
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            resValue("string", "host", "web.limoo.im")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        getByName("alpha") {
//            resValue("string", "host", "alpha.limonadapp.ir")
//            buildConfigField ("String", "BASE_FILE_SERVER_URL", "https://alpha.limonadapp.ir/fileserver/")
//            applicationIdSuffix = ".alpha"
//        }
//        getByName("debug") {
//            resValue("string", "host", "test.limonadapp.ir")
//            buildConfigField("String", "BASE_FILE_SERVER_URL", "https://test.limonadapp.ir/fileserver/")
//            applicationIdSuffix = ".debug"
//        }
    }

    buildFeatures {
        dataBinding = true
    }
    lint {
        abortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(Modules.core))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    implementation(project(Modules.cache))
    implementation(project(Modules.base))

    // Features
    implementation(project(Modules.login))
    implementation(project(Modules.profile))
    implementation(project(Modules.conversation))


    implementation(Libs.multidex)
    implementation(Libs.coil)
    implementation(Libs.hilt)
    kapt(Libs.hilt_kapt)
    debugImplementation(Libs.leak_canary)


}

kapt {
    correctErrorTypes = true
}