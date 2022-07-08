package com.emamagic.domain.interactors

import com.emamagic.core.Bridge
import com.emamagic.core.ResultWrapper
import com.emamagic.domain.entities.Attachment
import com.emamagic.domain.repositories.FileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadFile @Inject constructor(
    @Bridge
    private val fileRepository: FileRepository
) {

    operator fun invoke(params: Params): ResultWrapper<List<Attachment>> = fileRepository.uploadFile(params)

    data class Params(
        val data: String,
        val fileName: String
    )

}