package com.emamagic.data.network.services

import com.emamagic.domain.entities.WorkspaceEntity
import com.emamagic.domain.entities.WorkspaceUnread
import com.emamagic.domain.interactors.workspace.CreateWorkspace
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkspaceService {

    @GET("workspace/items/{workspace_id}")
    suspend fun findWorkspaceById(@Path("workspace_id") workspaceId: String): Response<WorkspaceEntity>

    @GET("workspace/items/{workspace_id}/unread")
    suspend fun getWorkspaceUnread(@Path("workspace_id") workspaceId: String): Response<WorkspaceUnread>

    @POST("workspace/items")
    suspend fun createWorkspace(@Body params: CreateWorkspace.Params): Response<WorkspaceEntity>

}