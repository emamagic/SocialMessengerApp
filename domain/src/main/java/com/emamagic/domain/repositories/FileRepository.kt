package com.emamagic.domain.repositories

import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.Attachment
import com.emamagic.domain.interactors.UploadFile
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun uploadFile(params: UploadFile.Params): Flow<ResultWrapper<List<Attachment>>>
}