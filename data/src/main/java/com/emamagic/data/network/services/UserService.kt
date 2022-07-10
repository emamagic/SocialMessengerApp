package com.emamagic.data.network.services

import com.emamagic.domain.entities.OrganizationEntity
import com.emamagic.domain.entities.User
import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.interactors.login.LoginViaPhoneNumber
import com.emamagic.domain.interactors.login.SignupUser
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("user/phone_verification")
    suspend fun loginWithPhoneNumber(@Body phoneNumber: LoginViaPhoneNumber.Params): Response<ResponseBody>

    @POST("j_spring_otptoken_security_check")
    suspend fun verifyOtp(
        @Query("j_phoneNumber") phoneNumber: String,
        @Query("j_otpToken") otpToken: String,
        @Query("j_deviceKey") deviceId: String,
    ): Response<User>

    @GET("user/items/self")
    suspend fun getCurrentUser(): Response<User>

    @GET("user/my_workspaces")
    suspend fun getMyWorkspaces(): Response<List<WorkspaceEntity>>

    @GET("user/my_organizations")
    suspend fun getMyOrganizations(): Response<List<OrganizationEntity>>

    @FormUrlEncoded
    @POST("j_spring_security_check")
    suspend fun loginWithUserName(
        @Field("j_username") username: String,
        @Field("j_password") password: String
    ): Response<ResponseBody>

    @POST("session/by_keycloak")
    suspend fun getSessionByKeycloak(): Response<ResponseBody>

    @GET("workspace/items/{workspace_id}")
    suspend fun getWorkspaceById(
        @Path("workspace_id") workspaceId: String
    ): WorkspaceEntity

    @POST("user/signup")
    suspend fun signup(@Body userInfo: SignupUser.Params): Response<User>

}