plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.commonJvm))
    implementation(Libs.retrofit)
    implementation(Libs.logging_interceptor)
    implementation(Libs.gson_converter)
    implementation(Libs.kotlin_coroutines)
    implementation(Libs.hilt)
    "kapt"(Libs.hilt_kapt)

}