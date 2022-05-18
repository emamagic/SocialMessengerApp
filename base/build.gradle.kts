plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(Modules.emoji))
    implementation(project(Modules.repository))
    api(project(Modules.androidCore))
    api(project(Modules.mvi))

    implementation(Libs.swipe_refresh)
    implementation(Libs.java_x)
    implementation(Libs.glide)
    kapt(Libs.glide_kapt)

    api(Libs.epoxy)
    kapt(Libs.epoxy_kapt)
    api(Libs.epoxy_data_binding)
    api(Libs.epoxy_paging)

}

kapt {
    correctErrorTypes = true
}