package com.emamagic.uploader

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.data.UploadNotificationConfig
import net.gotev.uploadservice.data.UploadNotificationStatusConfig
import net.gotev.uploadservice.observer.request.GlobalRequestObserver
import net.gotev.uploadservice.okhttp.OkHttpStack
import okhttp3.OkHttpClient
import java.util.concurrent.ThreadPoolExecutor

object UploaderFactory {

    private const val notificationChannelID = "LimooChannel"
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                notificationChannelID,
                "Limoo Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    // todo implement our httpStack and out threadPool
    fun create(application: Application, okHttpClient: OkHttpClient, threadPool: ThreadPoolExecutor) {
        createNotificationChannel(application)
        UploadServiceConfig.initialize(
            context = application,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )
        GlobalRequestObserver(application, GlobalUploadObserver())
        UploadServiceConfig.threadPool = threadPool
        UploadServiceConfig.httpStack = OkHttpStack(okHttpClient)
        UploadServiceConfig.notificationConfigFactory = { context, uploadId ->
            UploadNotificationConfig(
                isRingToneEnabled = true,
                notificationChannelId = UploadServiceConfig.defaultNotificationChannel!!,
                progress = UploadNotificationStatusConfig(
                    title = "لیمو",
                    message = "درحال آپلود فایل ..."
                ),
                success = UploadNotificationStatusConfig(
                    title = "لیمو",
                    message = "آپلود با موفقیت انجام شد"
                ),
                error = UploadNotificationStatusConfig(
                    title = "لیمو",
                    message = "آپلود ناموفق بود"
                ),
                cancelled = UploadNotificationStatusConfig(
                    title = "لیمو",
                    message = "آپلود کنسل شد"
                )
            )
        }
    }

}