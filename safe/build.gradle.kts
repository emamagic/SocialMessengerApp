plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

java {
    sourceCompatibility = Releases.javaVersion
    targetCompatibility = Releases.javaVersion
}
