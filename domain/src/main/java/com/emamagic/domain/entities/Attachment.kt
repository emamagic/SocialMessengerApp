package com.emamagic.domain.entities

class Attachment(
    val hash: String,
    val name: String,
    _mimeType: String? = null,
    val size: Long = 0,
    val messageId: String? = null,
    val conversationId: String? = null,
    val createAt: String? = null,
    val userId: String? = null,
    val hasPreviewImage: Boolean? = null,
    val threadRootId: String? = null
) {
    var url: String? = null
    var thumbnailUrl: String? = null
}