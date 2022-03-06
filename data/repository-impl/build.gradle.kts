plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.safe))
    implementation(project(Modules.network))
    implementation(project(Modules.cache))
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.repository))
    implementation(Libs.kotlin_reflect)
    implementation(Libs.java_x)

}