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
    implementation(project(Modules.core))
    implementation(Libs.swipe_refresh)
    api(project(Modules.coreAndroid))
    api(project(Modules.mvi))
    api(project(Modules.navigation))
    implementation(Libs.life_cycle_scope)

    api(Libs.epoxy)
    kapt(Libs.epoxy_kapt)
    api(Libs.epoxy_data_binding)
    api(Libs.epoxy_paging)
    implementation(Libs.hilt_core)
    kapt(Libs.hilt_kapt)

}

kapt {
    correctErrorTypes = true
}