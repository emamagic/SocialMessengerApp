plugins {
    id("com.android.library")
    kotlin ("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.safe))
    implementation(project(Modules.network))
    implementation(project(Modules.cache))
    implementation(project(Modules.core))
    implementation(project(Modules.repository))
    implementation(Libs.kotlin_reflect)
    implementation(Libs.hilt)
    kapt(Libs.hilt_kapt)

}