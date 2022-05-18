plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-common-build.gradle")
}
dependencies {

    implementation(Libs.hilt_core)
    kapt(Libs.hilt_kapt)

}