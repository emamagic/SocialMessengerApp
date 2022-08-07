package com.emamagic.domain.entities

import androidx.room.*

@Entity(
    tableName = "message",
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(
        entity = ConversationEntity::class,
        parentColumns = ["id"],
        childColumns = ["conversationId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["conversationId"])]
)
data class MessageEntity(
    override val id: String,
    val createAt: Long = 0,
    val updateAt: Long = 0,
    val deleteAt: Long = 0,
    val editAt: Long = 0,
    val userId: String,
    val conversationId: String,
    val threadRootId: String? = null,
    val directReplyMessage: DirectReplyMessage? = null,
    val text: String? = null,
    val type: String? = null,
    val pendingMessageId: String? = null,
    val props: Props? = null,
    val files: List<Attachment>? = null,
    var threadUId: String? = null,
    val forwardSourceMessage: ForwardSourceMessage? = null,
    val reactions: List<ReactionMessage>? = null,
    var previousMessageId: String? = null,
    var nextMessageId: String? = null,
    var timelineLabel: String? = null,
    val workspaceId: String? = null,
    val workspaceDisplayName: String? = null,
    val workspaceIconHash: String? = null,
    // this below field is used for timeline label color
    val labelColor: String? = null
): BaseEntity