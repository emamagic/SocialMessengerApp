plugins {
    id("com.android.library")
    kotlin ("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(Libs.room)
    implementation(Libs.room_coroutine)
    implementation(Libs.java_x)
    kapt(Libs.room_kapt)
    implementation(Libs.pref_manager)
    implementation(Libs.hawk)
    implementation(Libs.data_store_proto)
    implementation(Libs.data_store)

}