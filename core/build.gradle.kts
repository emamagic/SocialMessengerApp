plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(Libs.java_x)
    implementation(Libs.kotlin_coroutines)
}
