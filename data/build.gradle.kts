plugins {
    id("com.android.library")
    kotlin ("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    api(project(Modules.cookieJar))
    implementation(project(Modules.domain))
    implementation(Libs.gson_converter)
    implementation(Libs.room)
    implementation(Libs.room_coroutine)
    kapt(Libs.room_kapt)
    api(project(Modules.safe))
    implementation(project(Modules.cache))
    api(Libs.retrofit)
    implementation(Libs.hilt_core)
    implementation(Libs.gson)
    implementation(Libs.store_4)
    api(Libs.logging_interceptor)

}