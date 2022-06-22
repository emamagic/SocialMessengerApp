package com.emamagic.data_android.interceptor.network

import com.emamagic.core.AuthUserScope
import com.emamagic.core.resettableLazy
import com.emamagic.core.resettableManager
import com.emamagic.data_android.interceptor.Const
import com.emamagic.data_android.interceptor.di.RetrofitFactory
import com.emamagic.data_android.interceptor.network.services.ConfigService
import com.emamagic.data_android.interceptor.network.services.UserService
import com.google.gson.JsonObject
import dagger.Lazy
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestProvider @Inject constructor(
    private val retrofitFactory: RetrofitFactory,
    private val okHttpClient: Lazy<OkHttpClient>
) {

    // TODO remove tmp after adding pref
    private lateinit var TMP_HOST: String
    private lateinit var TMP_FILE_SERVER_ADDRESS: String

    private lateinit var BASE_URL: String
    private lateinit var API_URL: String
    private lateinit var BASE_FILE_SERVER_ADDRESS: String

    init {
        setBaseUrlAndApiUrl(Const.BASE_URL)
        setBaseFileServerUrl("https://test.limonadapp.ir/fileserver/")
    }

//    private val okhttpWithDownloadProgress: OkHttpClient by lazy {
//        okHttpClient.get().newBuilder().addInterceptor(DownloadProgressInterceptor(DownloadProgressResponseBody())).build()
//    }

    private val coordinatorLazyMgr = resettableManager()
    private val coordinatorRetrofit: Retrofit by resettableLazy(coordinatorLazyMgr) {
        retrofitFactory.create(BASE_URL, okHttpClient.get()).provideRetrofit()
    }

    private val lazyMgr = resettableManager()
    private val retrofit: Retrofit by resettableLazy(lazyMgr) {
        retrofitFactory.create(API_URL, okHttpClient.get()).provideRetrofit()
    }

//    private val coordinatorFileLazyMgr = resettableManager()
//    private val coordinatorFileRetrofit: Retrofit by resettableLazy(coordinatorFileLazyMgr) {
//        retrofitFactory.create(BASE_FILE_SERVER_ADDRESS, okHttpClient.get()).provideRetrofit()
//    }

    fun setBaseUrlAndApiUrl(host: String) {
        val mHost = "$host/Limonad/"
        BASE_URL = mHost
        API_URL = BASE_URL + "api/v1/"
        if (isHostChanged(host)) {
            coordinatorLazyMgr.reset()
        }
        TMP_HOST = mHost
    }

    fun setBaseFileServerUrl(fileServerAddress: String) {
        val mFileServerAddress = "$fileServerAddress/v1/"
        BASE_FILE_SERVER_ADDRESS = mFileServerAddress
//        if (isFileServerUrlChanged(fileServerAddress)) coordinatorFileLazyMgr.reset()
//        TMP_FILE_SERVER_ADDRESS = mFileServerAddress
    }

    private fun isHostChanged(host: String) =
        this::TMP_HOST.isInitialized && TMP_HOST != host

    private fun isFileServerUrlChanged(fileServerAddress: String) =
        this::TMP_FILE_SERVER_ADDRESS.isInitialized && TMP_FILE_SERVER_ADDRESS != fileServerAddress


    val userService: UserService
        get() = retrofit.create()
    val userServiceCoordinator: UserService
        get() = coordinatorRetrofit.create()
    val configService: ConfigService
        get() = retrofit.create()


    fun getAccessTokenRefresherCall(refreshToken: String): Call {
        val jsonType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonContent = JsonObject().toString()
        val body = jsonContent.toRequestBody(jsonType)
        val okhttpRequest = Request.Builder()
            .url("https://test.limonadapp.ir/Limonad/j_spring_jwt_security_check")
            .addHeader("Refresh", refreshToken)
            .post(body)
            .build()
        return okHttpClient.get()
            .newCall(okhttpRequest)
    }
}