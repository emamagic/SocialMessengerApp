plugins {
    id("com.android.library")
    kotlin("android")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {
    implementation(Libs.uploader)
    implementation(Libs.uploader_okhttp)
}