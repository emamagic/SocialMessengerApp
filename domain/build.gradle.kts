plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Releases.javaVersion
    targetCompatibility = Releases.javaVersion
}

dependencies {
    api(project(Modules.core))
    implementation(Libs.paging_common)
    implementation(Libs.room_common)
    implementation(Libs.gson)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.java_x)
    implementation(Libs.kotlin_coroutines)
}