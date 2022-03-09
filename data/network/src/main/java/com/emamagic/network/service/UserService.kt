package com.emamagic.network.service

import com.emamagic.entity.PhoneNumber
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/v1/user/phone_verification")
    suspend fun phoneVerification(@Body phoneNumber: PhoneNumber): Response<ResponseBody>


}