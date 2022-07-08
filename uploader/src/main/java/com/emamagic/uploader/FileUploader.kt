package com.emamagic.uploader

import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest

object FileUploader {

    fun upload(params: UploaderParams): String {
        return MultipartUploadRequest(params.context, params.serverUrl)
            .setMethod("POST")
            .addFileToUpload(params.data, params.fileName)
            .startUpload()
    }

}