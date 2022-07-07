package com.emamagic.common_ui

interface PermissionListener {
    fun onPermissionGranted(resultMap: Map<String, Boolean>)
}
interface PermissionListener2 {
    fun onPermissionGranted(isGranted: Boolean)
}