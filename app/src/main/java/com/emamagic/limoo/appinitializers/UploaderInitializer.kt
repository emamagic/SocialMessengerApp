package com.emamagic.limoo.appinitializers

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.emamagic.common_ui.appinitializer.AppInitializer
import dagger.Lazy
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.data.UploadNotificationConfig
import net.gotev.uploadservice.data.UploadNotificationStatusConfig
import net.gotev.uploadservice.okhttp.OkHttpStack
import okhttp3.OkHttpClient
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject

class UploaderInitializer @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val threadPoolExecutor: ThreadPoolExecutor
): AppInitializer {
    override fun init(application: Application) {
        val notificationChannelID = "LimooChannel"
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                notificationChannelID,
                "Limoo Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        UploadServiceConfig.initialize(
            context = application,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )
        UploadServiceConfig.threadPool = threadPoolExecutor
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