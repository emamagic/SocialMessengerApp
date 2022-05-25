import com.google.protobuf.gradle.*
plugins {
    id("com.android.library")
    kotlin ("android")
    id ("com.google.protobuf") version "0.8.17"
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.core))
    implementation(Libs.java_x)
    implementation(Libs.pref_manager)
    implementation(Libs.hawk)
    implementation(Libs.data_store_proto)
    implementation(Libs.data_store)
    api(Libs.proto)

}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.15.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}