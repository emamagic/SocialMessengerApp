package com.emamagic.data.repositories

import android.content.Context
import android.util.Log
import com.emamagic.core.AuthUserScope
import com.emamagic.core.ResultWrapper
import com.emamagic.data.network.RestProvider
import com.emamagic.domain.entities.Attachment
import com.emamagic.domain.interactors.UploadFile
import com.emamagic.domain.repositories.FileRepository
import com.emamagic.uploader.FileUploader
import com.emamagic.uploader.UploaderParams
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@AuthUserScope
class FileRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    @ApplicationContext
    private val context: Context
): FileRepository {

    override fun uploadFile(params: UploadFile.Params): ResultWrapper<List<Attachment>> {
        val uploadId = FileUploader.upload(UploaderParams(context, "${restProvider.baseFileServerUrl}files", params.data ,params.fileName))
        Log.e("TAG", "uploadFile: $uploadId")
        return ResultWrapper.Success(emptyList())
    }


}