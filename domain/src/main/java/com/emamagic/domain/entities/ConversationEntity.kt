package com.emamagic.domain.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "conversation")
data class ConversationEntity(
    @PrimaryKey
    override val id: String,
    val workspaceId: String,
    val archived: Boolean?,
    val createAt: Long?,
    val creatorId: String?,
    val deleteAt: Long?,
    val directUserAvailability: String?,
    val directUserDisplayName: String?,
    val directUserId: String?,
    val directUserLastActivityDate: String?,
    val directUserLastSeenAt: String?,
    val displayName: String?,
    val header: String?,
    val iconHash: String?,
    val myMembership: MyMembership?,
    val nonAdminsCanSend: Boolean?,
    val organizationId: String?,
    val purpose: String?,
    val totalMsgCount: Int?,
    val type: String?,
    val updateAt: String?
): BaseEntity