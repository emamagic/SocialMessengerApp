package com.emamagic.data.network.services

import com.emamagic.domain.entities.MessageEntity
import retrofit2.Response
import retrofit2.http.*

interface MessageService {


//    @POST("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items")
//    suspend fun create(
//        @Path("workspace_id") workspaceId: String?,
//        @Path("conversation_id") conversationId: String?,
//        @Body uploadMessage: MessageUploader2.UploadMessage?
//    ): Observable<Message?>?
//
//    @DELETE("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items/{message_id}")
//    suspend fun delete(
//        @Path("workspace_id") workspaceId: String?,
//        @Path("conversation_id") conversationId: String?,
//        @Path("message_id") messageId: String?
//    ): Observable<String?>?

//    @POST("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items/{message_id}")
//    suspend fun update(
//        @Path("workspace_id") workspaceId: String?,
//        @Path("conversation_id") conversationId: String?,
//        @Path("message_id") messageId: String?,
//        @Body message: UpdateMessage?
//    ): String

    @GET("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items")
    suspend fun getPageBeforeMessage(
        @Path("workspace_id") workspaceId: String?,
        @Path("conversation_id") conversationId: String?,
        @Query("page") page: String?,
        @Query("per_page") perPage: String?,
        @Query("before") messageId: String?
    ): List<MessageEntity>

    @GET("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items")
    suspend fun getPageBeforeMessage(
        @Path("workspace_id") workspaceId: String?,
        @Path("conversation_id") conversationId: String?,
        @Query("page") page: String?,
        @Query("per_page") perPage: String?,
        @Query("before_date") beforeDate: Long
    ): List<MessageEntity>

    @GET("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items")
    suspend fun getPageAfterMessage(
        @Path("workspace_id") workspaceId: String?,
        @Path("conversation_id") conversationId: String?,
        @Query("page") page: String?,
        @Query("per_page") perPage: String?,
        @Query("after") messageId: String?
    ): List<MessageEntity>

    @GET("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items")
    suspend fun getPageAfterMessage(
        @Path("workspace_id") workspaceId: String?,
        @Path("conversation_id") conversationId: String?,
        @Query("page") page: String?,
        @Query("per_page") perPage: String?,
        @Query("since") since: Long
    ): List<MessageEntity>

    @GET("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/items/{message_id}")
    suspend fun getSingleMessage(
        @Path("workspace_id") workspaceId: String?,
        @Path("conversation_id") conversationId: String?,
        @Path("message_id") messageId: String?
    ): MessageEntity

//    @POST("workspace/items/{workspace_id}/conversation/items/{conversation_id}/message/forward")
//    suspend fun forward(
//        @Path("workspace_id") workspaceId: String?,
//        @Path("conversation_id") conversationId: String?,
//        @Body forwardMessage: ForwardMessage?
//    ): Observable<Response<Void?>?>?

//    @POST("workspace/items/{workspace_id}/message_search")
//    suspend fun searchForMessage(
//        @Path("workspace_id") workspaceId: String?,
//        @Body searchRequest: MessageSearchRequest?,
//        @Query("page") page: String?,
//        @Query("per_page") perPage: String?
//    ): Observable<List<Message?>?>?
}