package com.emamagic.domain.entities

import com.google.gson.annotations.SerializedName

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
    val threadRootId: String? = null,
//    val metadata: JSONObject? = null
) {
//
//    @SerializedName(value = "mime_type", alternate = ["contentType"])
//    val mimeType: String = _mimeType ?: MediaHelper.getMimeType(name) ?: ""
//        get() {
//            //field set by reflection can be null
//            return field ?: (MediaHelper.getMimeType(name) ?: "")
//        }

}