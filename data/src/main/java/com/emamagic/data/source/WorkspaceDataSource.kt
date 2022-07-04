package com.emamagic.data.source

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.emamagic.data.db.dao.WorkspaceDao
import com.emamagic.data.network.RestProvider
import javax.inject.Inject

class WorkspaceDataSource @Inject constructor(
    private val restProvider: RestProvider,
    private val workspaceDao: WorkspaceDao
) {

//    val workspaceStore = StoreBuilder.from(
//        fetcher = Fetcher.of {
//            restProvider.userService.getMyWorkspaces()
//        },
//        sourceOfTruth = SourceOfTruth.of(
//            reader = { workspaceDao.getWorkspaces() },
//            writer = { key, workspaces -> workspaceDao.insert(workspaces) },
//            delete = workspaceDao::delete,
//            deleteAll = workspaceDao::deleteAll
//        )
//    ).build()


}