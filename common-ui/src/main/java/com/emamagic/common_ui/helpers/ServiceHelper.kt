package com.emamagic.common_ui.helpers

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.PowerManager
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

object ServiceHelper {

    fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun getWindowManager(context: Context): WindowManager {
        return context.getSystemService(Activity.WINDOW_SERVICE) as WindowManager
    }

    fun getConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun getTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    fun getAudioManager(context: Context): AudioManager {
        return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun getPowerManager(context: Context): PowerManager {
        return context.getSystemService(Context.POWER_SERVICE) as PowerManager
    }

    fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun getVibrator(context: Context): Vibrator {
        return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

}