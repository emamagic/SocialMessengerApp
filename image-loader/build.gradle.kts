plugins {
    id("com.android.library")
    kotlin("android")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {
    implementation(Libs.coil)
}