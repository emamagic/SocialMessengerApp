plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(Libs.navigation_component_fragment)

}