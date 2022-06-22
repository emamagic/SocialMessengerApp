package com.emamagic.data_android.interceptor.network.services

import com.emamagic.domain.entities.User
import com.emamagic.domain.entities.Workspace
import com.emamagic.domain.interactors.LoginWithPhoneNumber
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("user/phone_verification")
    suspend fun loginWithPhoneNumber(@Body phoneNumber: LoginWithPhoneNumber.Params): Response<ResponseBody>

    @POST("j_spring_otptoken_security_check")
    suspend fun verifyOtp(
        @Query("j_phoneNumber") phoneNumber: String,
        @Query("j_otpToken") otpToken: String,
        @Query("j_deviceKey") deviceId: String,
    ): Response<User>

    @GET("user/items/self")
    suspend fun getCurrentUser(): Response<User>

    @GET("user/my_workspaces")
    suspend fun getMyWorkspaces(): Response<List<Workspace>>

    @FormUrlEncoded
    @POST("j_spring_security_check")
    suspend fun loginWithUserName(
        @Field("j_username") username: String,
        @Field("j_password") password: String
    ): Response<ResponseBody>

}