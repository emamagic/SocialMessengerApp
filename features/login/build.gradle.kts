apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {

    "api"(project(Modules.countryPicker))

}