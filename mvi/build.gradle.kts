plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    api(project(Modules.navigation))
    api(project(Modules.core))

}