plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(Modules.emoji))
    implementation(project(Modules.repository))
    implementation(project(Modules.entity))
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.navigation))

    implementation(Libs.swipe_refresh)
    implementation(Libs.java_x)
    implementation(Libs.glide)
    kapt(Libs.glide_kapt)
}

kapt {
    correctErrorTypes = true
}