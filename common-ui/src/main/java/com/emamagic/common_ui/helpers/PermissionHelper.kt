package com.emamagic.common_ui.helpers

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.emamagic.common_ui.PermissionListener

object PermissionHelper {

    private var isFirstTime = true


    fun hasPermission(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    fun hasPermission(context: Context, permission: String) : Boolean =
        ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED




    fun getPermissions(
        fr: Fragment,
        permissionListener: PermissionListener,
        vararg permissions: String
    ) {
        val requestPermissionLauncher =
            fr.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
               permissionListener.onPermissionGranted(it)
            }

        /**
         * ActivityCompat.shouldShowRequestPermissionRationale
         * true -> When the user has denied the permission previously but has not checked the "Never Ask Again" checkbox.
         * false -> 1. When the user has denied the permission previously AND never ask again checkbox was selected.
         *          2. When the user is requesting permission for the first time.
         * */
//        if (!ActivityCompat.shouldShowRequestPermissionRationale(fr, permission) && isFirstTime) {
//            isFirstTime = false
//        }
//
//        if (!ActivityCompat.shouldShowRequestPermissionRationale(fr, permission) && !isFirstTime) {
//            permissionListener.givePermissionManually()
//            return
//        }

        requestPermissionLauncher.launch(permissions)

    }

}

