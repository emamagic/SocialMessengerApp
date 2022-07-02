package com.emamagic.data.db

import androidx.room.TypeConverter
import com.emamagic.domain.entities.MyMembership
import com.emamagic.domain.entities.WorkerNode
import com.google.gson.Gson

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

}