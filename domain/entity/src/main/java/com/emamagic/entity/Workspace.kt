package com.emamagic.entity

data class Workspace(
    val defaultConversationId: String,
    val description: String,
    val displayName: String,
    val iconHash: String,
    val id: String,
    val invitationStyle: String,
    val isInvitation: Boolean,
    val myMembership: MyMembership,
    val name: String,
    val organizationId: String,
    val timelineActive: Boolean,
    val timelineConversationId: String,
    val workerNode: WorkerNode
)
data class WorkerNode(
    val apiUrl: String,
    val fileUrl: String,
    val websocketUrl: String
)
data class MyMembership(
    val availability: String,
    val avatarHash: String,
    val deleteAt: Long,
    val email: String,
    val lastActivityDate: Long,
    val nickname: String,
    val notifyProps: NotifyProps,
    val phoneNumber: String,
    val position: String,
    val roles: List<String>,
    val userId: String,
    val workspaceId: String
)