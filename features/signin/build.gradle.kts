apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {

    "implementation"(project(Modules.countryPicker))

}