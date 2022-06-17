package com.emamagic.data_android.interceptor.network.services

import com.emamagic.domain.entities.PhoneNumber
import com.emamagic.domain.entities.User
import com.emamagic.domain.entities.Workspace
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("user/phone_verification")
    suspend fun phoneRegistration(@Body phoneNumber: PhoneNumber): Response<ResponseBody>

    @POST("j_spring_otptoken_security_check")
    suspend fun otpVerification(
        @Query("j_phoneNumber") phoneNumber: String,
        @Query("j_otpToken") otpToken: String,
        @Query("j_deviceKey") deviceId: String,
    ): Response<User>

    @GET("user/items/self")
    suspend fun getCurrentUser(): Response<User>

    @GET("user/my_workspaces")
    suspend fun getMyWorkspaces(): Response<List<Workspace>>

}