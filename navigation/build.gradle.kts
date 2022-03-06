plugins {
    id ("com.android.library")
    id("androidx.navigation.safeargs")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    api (Libs.navigation_component_fragment)
    api (Libs.navigation_component_ui)

}