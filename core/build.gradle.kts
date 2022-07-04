plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Releases.javaVersion
    targetCompatibility = Releases.javaVersion
}

dependencies {
    implementation(Libs.java_x)
    implementation(Libs.kotlin_coroutines)
}
