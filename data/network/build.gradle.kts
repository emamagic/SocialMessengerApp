plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.commonJvm))
    api(project(Modules.cookieJar))
    api(Libs.retrofit)
    api(Libs.logging_interceptor)
    api(Libs.gson_converter)
    implementation(Libs.java_x)

}