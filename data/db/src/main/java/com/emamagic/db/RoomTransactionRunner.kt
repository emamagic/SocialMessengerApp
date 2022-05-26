package com.emamagic.db
//
//import androidx.room.withTransaction
//import javax.inject.Inject
//
//class RoomTransactionRunner @Inject constructor(
//    private val db: TiviRoomDatabase
//) : DatabaseTransactionRunner {
//    override suspend operator fun <T> invoke(block: suspend () -> T): T {
//        return db.withTransaction {
//            block()
//        }
//    }
//}