package com.emamagic.data.db

import androidx.room.TypeConverter
import com.emamagic.domain.entities.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromWorkerNode(workerNode: WorkerNode?): String? {
        return if (workerNode == null) null else Gson().toJson(workerNode)
    }

    @TypeConverter
    fun toWorkerNode(value: String?): WorkerNode? {
        return if (value == null) null else Gson()
            .fromJson(value, WorkerNode::class.java)
    }

    @TypeConverter
    fun toWorkspaceMembership(value: String?): MyMembership? {
        return if (value == null) null else Gson()
            .fromJson(value, MyMembership::class.java)
    }

    @TypeConverter
    fun fromWorkspaceMembership(workspaceMembership: MyMembership?): String? {
        return if (workspaceMembership == null) null else Gson()
            .toJson(workspaceMembership)
    }

    @TypeConverter
    fun toDirectReplyMessage(value: String?): DirectReplyMessage? {
        return if (value == null) null else Gson()
            .fromJson(value, DirectReplyMessage::class.java)
    }

    @TypeConverter
    fun fromDirectReplyMessage(directReplyMessage: DirectReplyMessage?): String? {
        return if (directReplyMessage == null) null else Gson()
            .toJson(directReplyMessage)
    }

    @TypeConverter
    fun toAttachment(value: String?): List<Attachment>? {
        return if (value == null) null else Gson()
            .fromJson(value, object : TypeToken<List<Attachment>?>() {}.type)
    }

    @TypeConverter
    fun fromAttachment(attachment: List<Attachment>?): String? {
        return if (attachment == null) null else Gson()
            .toJson(attachment, object : TypeToken<List<Attachment>?>() {}.type)
    }

    @TypeConverter
    fun fromForwardSourceMessage(forwardSourceMessage: ForwardSourceMessage?): String? {
        return if (forwardSourceMessage == null) null else Gson()
            .toJson(forwardSourceMessage)
    }

    @TypeConverter
    fun toForwardSourceMessage(value: String?): ForwardSourceMessage? {
        return if (value == null) null else Gson()
            .fromJson(value, ForwardSourceMessage::class.java)
    }

    @TypeConverter
    fun fromReactions(reactions: List<ReactionMessage?>?): String? {
        return if (reactions == null) null else Gson().toJson(reactions)
    }

    @TypeConverter
    fun toReactions(value: String?): List<ReactionMessage?>? {
        return if (value == null) null else Gson()
            .fromJson(value, object : TypeToken<List<ReactionMessage?>?>() {}.type)
    }

    @TypeConverter
    fun toProps(value: String?): Props? {
        return if (value == null) null else Gson().fromJson(value, Props::class.java)
    }

    @TypeConverter
    fun fromProps(prop: Props?): String? {
        return if (prop == null) null else Gson().toJson(prop, Props::class.java)
    }

}