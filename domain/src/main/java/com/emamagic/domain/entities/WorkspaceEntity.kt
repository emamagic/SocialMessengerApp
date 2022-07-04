package com.emamagic.domain.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "workspace")
data class WorkspaceEntity(
    @PrimaryKey
    override val id: String,
    val defaultConversationId: String? = null,
    val description: String? = null,
    val displayName: String? = null,
    val iconHash: String? = null,
    val invitationStyle: String? = null,
    val isInvitation: Boolean = false,
    val myMembership: MyMembership? = null,
    val name: String? = null,
    val organizationId: String? = null,
    val timelineActive: Boolean? = null,
    val timelineConversationId: String? = null,
    val workerNode: WorkerNode? = null
) : BaseEntity

data class WorkerNode(
    val apiUrl: String? = null,
    val fileUrl: String? = null,
    val websocketUrl: String? = null
)

data class MyMembership(
    val availability: String? = null,
    val avatarHash: String? = null,
    val deleteAt: Long? = null,
    val email: String? = null,
    val lastActivityDate: Long? = null,
    val nickname: String? = null,
    val notifyProps: NotifyProps? = null,
    val phoneNumber: String? = null,
    val position: String? = null,
    val roles: List<String>? = null,
    val userId: String? = null,
    val workspaceId: String? = null
)