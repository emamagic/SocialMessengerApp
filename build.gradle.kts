// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.jfrog.org/libs-snapshot")
        jcenter()
    }
    dependencies {
        classpath(Build.tools)
        classpath(Build.safe_args)
        classpath(Build.hilt)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        // NOTE: Do not place your base dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}