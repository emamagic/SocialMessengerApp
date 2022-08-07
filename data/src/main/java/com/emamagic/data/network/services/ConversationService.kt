package com.emamagic.data.network.services

import com.emamagic.domain.entities.ConversationEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConversationService {

    @GET("workspace/items/{workspace_id}/conversation/items")
    suspend fun getConversations(
        @Path("workspace_id") workspaceId: String,
        @Query("include_archived") includeArchived: Boolean = true
    ): List<ConversationEntity>
}