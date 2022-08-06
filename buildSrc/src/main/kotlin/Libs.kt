object Libs {

    // Base
    const val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val androidx_compat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    // Navigation Component
    const val navigation_component_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigation_component_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"

    // Hilt
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_kapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hilt_nav_graph_scope = "androidx.hilt:hilt-navigation-fragment:${Versions.hilt_nav_graph_scope}"
    const val hilt_core = "com.google.dagger:hilt-core:${Versions.hilt}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_kapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.moshi}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.gson}"

    // Gson
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Logging Interceptor
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Multidex
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"

    // JavaX
    const val java_x = "javax.inject:javax.inject:${Versions.java_x}"

    // Kotlin Reflect
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    // LifeCycle Scope
    const val life_cycle_scope = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.coroutine_scope}"
    const val life_cycle_scope_common = "androidx.lifecycle:lifecycle-common:${Versions.coroutine_scope}"
    const val life_cycle_process = "androidx.lifecycle:lifecycle-process:${Versions.coroutine_scope}"

    // ViewModel Scope
    const val view_model_scope = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.coroutine_scope}"

    // Kotlin Coroutines
    const val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    // Store 4
    const val store_4 = "com.dropbox.mobile.store:store4:${Versions.store_version}"

    // SwipeRefresh
    const val swipe_refresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh}"

    // Auto Image Slider
    const val auto_image_slider = "com.github.smarteist:autoimageslider:${Versions.auto_image_slider}"

    // Shimmer
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

    // Circle Image View
    const val circle_image_view = "de.hdodenhof:circleimageview:${Versions.circle_image_view}"

    // Epoxy
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    const val epoxy_kapt = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
    const val epoxy_data_binding = "com.airbnb.android:epoxy-databinding:${Versions.epoxy}"
    const val epoxy_paging = "com.airbnb.android:epoxy-paging3:${Versions.epoxy}"
    const val paging_common = "androidx.paging:paging-common:${Versions.paging}"

    // Paging
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"

    // Annotation
    const val annotationx = "androidx.annotation:annotation:${Versions.annotation}"

    // Room
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val room_kapt = "androidx.room:room-compiler:${Versions.room}"
    const val room_coroutine = "androidx.room:room-ktx:${Versions.room}"
    const val room_common = "androidx.room:room-common:${Versions.room}"
    const val room_paging = "androidx.room:room-paging:${Versions.room}"

    // Lottie
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    // WorkManager
    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work_manager}"

    // LeakCanary
    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"

    // Pref
    const val pref_manager = "androidx.preference:preference-ktx:${Versions.pref_manager}"
    const val hawk = "com.orhanobut:hawk:${Versions.hawk}"
//    const val data_store = "androidx.datastore:datastore-preferences:${Versions.data_store}"
//    const val data_store_proto = "androidx.datastore:datastore:${Versions.data_store}"
//    const val proto = "com.google.protobuf:protobuf-javalite:${Versions.proto}"

    // PersistentCookieJar
    const val persistent_cookie_jar = "com.github.franmontiel:PersistentCookieJar:${Versions.persistent_cookie_jar}"

    // Rxjava
    const val rx_java = "io.reactivex.rxjava3:rxjava:${Versions.rx_java}"
    const val rx_android = "io.reactivex.rxjava3:rxandroid:${Versions.rx_java}"

    // Coil
    const val coil = "io.coil-kt:coil:${Versions.coil}"

    // AppAuth
    const val appAuth = "net.openid:appauth:${Versions.appAuth}"

    // Uploader
    const val uploader = "net.gotev:uploadservice:${Versions.uploader}"
    const val uploader_okhttp = "net.gotev:uploadservice-okhttp:${Versions.uploader}"
}
