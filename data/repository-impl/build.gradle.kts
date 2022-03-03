plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.safe))
    implementation(project(Modules.network))
    implementation(project(Modules.cache))
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.entity))
    implementation(project(Modules.repository))
    implementation(Libs.retrofit)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.store_4)
    implementation(Libs.hilt)
    kapt(Libs.hilt_kapt)

}