package com.emamagic.base

interface PermissionListener {
    fun onPermissionGranted(resultMap: Map<String, Boolean>)
}
interface PermissionListener2 {
    fun onPermissionGranted(isGranted: Boolean)
}