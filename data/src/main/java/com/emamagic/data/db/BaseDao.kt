package com.emamagic.data.db

import androidx.room.*
import com.emamagic.domain.entities.BaseEntity

interface BaseDao<ENTITY: BaseEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ENTITY): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<ENTITY>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSoft(item: ENTITY): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSoft(items: List<ENTITY>): List<Long>

    @Delete
    suspend fun delete(item: ENTITY)

    @Delete
    suspend fun deleteEntity(item: ENTITY): Int

    @Update
    suspend fun update(item: ENTITY)

    @Update
    suspend fun update(items: List<ENTITY?>)

    @Transaction
    suspend fun withTransaction(tx: suspend () -> Unit) = tx()
}

@Transaction
suspend inline fun <reified ENTITY: BaseEntity> BaseDao<ENTITY>.upsert(item: ENTITY) {
    if (insert(item) != -1L) return
    update(item)
}

@Transaction
suspend inline fun <reified ENTITY: BaseEntity> BaseDao<ENTITY>.upsert(items: List<ENTITY>) {
    val insertResult = insert(items)
    val updateList = mutableListOf<ENTITY>()
    for (i in insertResult.indices) {
        if (insertResult[i] == -1L) updateList.add(items[i])
    }
    if (updateList.isNotEmpty()) update(updateList)
}

@Transaction
suspend inline fun <reified ENTITY: BaseEntity> BaseDao<ENTITY>.insertOrDelete(item: ENTITY) {
    val insertResult = insert(item)
    if (insertResult == -1L) delete(item)
}

