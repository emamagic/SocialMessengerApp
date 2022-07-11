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
    api(Libs.room)
    implementation(Libs.room_coroutine)
    kapt(Libs.room_kapt)
    kapt(Libs.room_paging)
    api(project(Modules.safe))
    implementation(project(Modules.cache))
    api(Libs.retrofit)
    implementation(Libs.hilt)
//    kapt(Libs.hilt_kapt)
    implementation(Libs.gson)
    implementation(Libs.store_4)
    api(Libs.logging_interceptor)
    implementation(Libs.uploader)
    implementation(Libs.life_cycle_process)

}