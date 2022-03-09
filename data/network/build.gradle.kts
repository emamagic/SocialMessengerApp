plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.commonJvm))
    implementation(project(Modules.safe))
    api(Libs.retrofit)
    api(Libs.logging_interceptor)
    api(Libs.gson_converter)
    implementation(Libs.java_x)

}