package com.emamagic.domain.entities

data class WorkspaceUnread(
    var workspaceId: String,
    var msgCount: Int,
    var mention_count: Int,
    var unreadThreadCount: Int,
    var threadMentionCount: Int,
    var isMute: Boolean
)