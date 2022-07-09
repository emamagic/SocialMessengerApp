package com.emamagic.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.emamagic.core.AuthUserScope
import com.emamagic.core.ResultWrapper
import com.emamagic.data.network.RestProvider
import com.emamagic.domain.entities.Attachment
import com.emamagic.domain.interactors.UploadFile
import com.emamagic.domain.repositories.FileRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import javax.inject.Inject

// todo data module is not sealed from our uploader library
// todo logger for logging uploader [uploader service has its own logger]
@AuthUserScope
class FileRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    @ApplicationContext
    private val context: Context
) : FileRepository {

    private fun toAttachments(bodyString: String): List<Attachment> =
        Gson().fromJson(bodyString, Array<Attachment>::class.java).asList().also {
            it.map { attachment ->
                attachment.url = "${fileServerUrl}?mode=view&hash=${attachment.hash}"
                attachment.thumbnailUrl = "${fileServerUrl}/thumbnail??mode=view*hash=${attachment.hash}"
            }
        }

    private val fileServerUrl get() = "${restProvider.baseFileServerUrl}files"

    override fun uploadFile(params: UploadFile.Params): Flow<ResultWrapper<List<Attachment>>> =
        callbackFlow {
            val observer =
                MultipartUploadRequest(context, fileServerUrl).apply {
                    params.filesPath.forEach { filePath ->
                        addFileToUpload(filePath, "file[]")
                    }
                }
                    .setMethod("POST")
                    .subscribe(
                        context,
                        ProcessLifecycleOwner.get(),
                        object : RequestObserverDelegate {

                            override fun onProgress(context: Context, uploadInfo: UploadInfo) {

                            }

                            override fun onSuccess(
                                context: Context,
                                uploadInfo: UploadInfo,
                                serverResponse: ServerResponse
                            ) {
                                trySend(ResultWrapper.Success(toAttachments(serverResponse.bodyString)))
                            }

                            override fun onError(
                                context: Context,
                                uploadInfo: UploadInfo,
                                exception: Throwable
                            ) {

                            }

                            override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
                                channel.close()
                            }

                            override fun onCompletedWhileNotObserving() {}
                        })

//            awaitClose { observer.unregister() }
        }


}