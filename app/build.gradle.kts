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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(Modules.network))
    implementation(project(Modules.repositoryImpl))
    implementation(project(Modules.cache))
    implementation(project(Modules.repository))
    implementation(project(Modules.safe))
    implementation(project(Modules.base))

    // Features
    implementation(project(Modules.signin))
    implementation(project(Modules.signup))
    implementation(project(Modules.conversation))


    implementation(Libs.multidex)
    implementation(Libs.hilt)
    kapt(Libs.hilt_kapt)
    debugImplementation(Libs.leak_canary)


}

kapt {
    correctErrorTypes = true
}