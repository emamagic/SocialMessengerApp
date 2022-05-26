package com.emamagic.cache.cache.data_store

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.emamagic.cache.User
import com.emamagic.cache.cache.CacheInitializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

val store: DataStore<Preferences> by lazy { CacheInitializer.getStore() }

object StoreKeys {
    val SORT_ORDER = stringPreferencesKey("sort_order")
    val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
}

private val userStore: DataStore<User> by lazy { CacheInitializer.getUserStore() }

val getUser: Flow<User> = userStore.data.catch { exception ->
    if (exception is IOException) {
        Log.e("Error", exception.message.toString())
        emit(User.getDefaultInstance())
    } else {
        throw exception
    }
}

suspend fun setUser(user: com.emamagic.entity.User) {
    userStore.updateData { preference ->
      val userBuilder =  preference.toBuilder()
        if (user.id.isNotEmpty()) userBuilder.id = user.id
        if (!user.username.isNullOrEmpty()) userBuilder.username = user.username
        if (!user.firstName.isNullOrEmpty()) userBuilder.firstName = user.firstName
        if (!user.lastName.isNullOrEmpty()) userBuilder.lastName = user.lastName
        if (!user.nickname.isNullOrEmpty()) userBuilder.nickName = user.nickname
        if (!user.phoneNumber.isNullOrEmpty()) userBuilder.phoneNumber = user.phoneNumber
        if (!user.avatarHash.isNullOrEmpty()) userBuilder.avatarHash = user.avatarHash
        if (!user.email.isNullOrEmpty()) userBuilder.email = user.email
        userBuilder.build()
    }
}






