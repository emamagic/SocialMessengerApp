plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/android-common-build.gradle")
}


dependencies {

    implementation(project(Modules.commonJvm))
    implementation(Libs.retrofit)
    implementation(Libs.kotlin_coroutines)

}