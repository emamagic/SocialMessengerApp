package com.emamagic.file_manager

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class FileAccessor constructor(
    private val lifecycleOwner: LifecycleOwner,
    private val registry: ActivityResultRegistry
): DefaultLifecycleObserver {

    private val mGetContent: ActivityResultLauncher<String>? = null

    private val imagePicker = registry.register("key", lifecycleOwner, ActivityResultContracts.GetContent()) {

    }
    fun selectImage() {
        imagePicker.launch("image/*")
    }

}