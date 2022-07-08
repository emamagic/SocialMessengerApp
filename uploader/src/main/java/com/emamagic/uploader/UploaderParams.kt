package com.emamagic.uploader

import android.content.Context

data class UploaderParams(
    val context: Context,
    val serverUrl: String,
    val data: String,
    val fileName: String
)
