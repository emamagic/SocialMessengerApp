plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
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